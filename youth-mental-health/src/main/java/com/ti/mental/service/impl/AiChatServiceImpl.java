package com.ti.mental.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.entity.AiChat;
import com.ti.mental.mapper.AiChatMapper;
import com.ti.mental.service.AiChatService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AI聊天Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class AiChatServiceImpl extends ServiceImpl<AiChatMapper, AiChat> implements AiChatService {

    @Resource
    private ChatClient.Builder chatClientBuilder;

    private static final String SYSTEM_PROMPT = """
            你是一位专业的青少年心理咨询师，名叫"心理小助手"。你的职责是：
            1. 倾听青少年的心理困扰，给予温暖和支持
            2. 提供专业但易于理解的心理健康建议
            3. 在必要时建议寻求专业心理咨询帮助
            4. 保持积极、友善、耐心的态度
            5. 使用简单易懂的语言，避免专业术语
            6. 关注青少年的情绪变化，给予适当的情感支持
            
            注意事项：
            - 不要提供医疗诊断或开药建议
            - 如果发现有自伤或伤害他人的倾向，请建议立即寻求专业帮助
            - 保持对话的私密性和安全性
            - 回复要温暖、有同理心，让青少年感到被理解和支持
            """;

    @Override
    public String chat(Long userId, String sessionId, String message) {
        // 保存用户消息并获取sessionId
        sessionId = saveUserMessage(userId, sessionId, message);

        // 构建消息列表
        List<Message> messages = buildMessages(userId, sessionId);

        // 调用AI获取回复
        ChatClient chatClient = chatClientBuilder.build();
        String reply;
        try {
            reply = chatClient.prompt()
                    .messages(messages)
                    .call()
                    .content();
        } catch (Exception e) {
            reply = "抱歉，我现在有点忙，请稍后再试。如果你有紧急情况，请联系专业心理咨询师或拨打心理援助热线。";
        }

        // 保存AI回复
        saveAssistantMessage(userId, sessionId, reply);

        return reply;
    }

    @Override
    public String saveUserMessage(Long userId, String sessionId, String message) {
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = IdUtil.fastSimpleUUID();
        }
        AiChat userChat = new AiChat();
        userChat.setUserId(userId);
        userChat.setSessionId(sessionId);
        userChat.setRole("user");
        userChat.setContent(message);
        this.save(userChat);
        return sessionId;
    }

    @Override
    public List<Message> buildMessages(Long userId, String sessionId) {
        List<AiChat> history = getHistory(userId, sessionId);
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(SYSTEM_PROMPT));
        int start = Math.max(0, history.size() - 20);
        for (int i = start; i < history.size(); i++) {
            AiChat chat = history.get(i);
            if ("user".equals(chat.getRole())) {
                messages.add(new UserMessage(chat.getContent()));
            } else {
                messages.add(new AssistantMessage(chat.getContent()));
            }
        }
        return messages;
    }

    @Override
    public void saveAssistantMessage(Long userId, String sessionId, String content) {
        AiChat assistantChat = new AiChat();
        assistantChat.setUserId(userId);
        assistantChat.setSessionId(sessionId);
        assistantChat.setRole("assistant");
        assistantChat.setContent(content);
        this.save(assistantChat);
    }

    @Override
    public List<AiChat> getHistory(Long userId, String sessionId) {
        return this.lambdaQuery()
                .eq(AiChat::getUserId, userId)
                .eq(AiChat::getSessionId, sessionId)
                .orderByAsc(AiChat::getCreateTime)
                .list();
    }

    @Override
    public List<String> getSessionIds(Long userId) {
        // 查询该用户的所有会话记录，按 ID 倒序排列
        List<AiChat> allRecords = this.lambdaQuery()
                .select(AiChat::getSessionId, AiChat::getCreateTime)
                .eq(AiChat::getUserId, userId)
                .orderByDesc(AiChat::getCreateTime)
                .list();
        
        // 使用 LinkedHashSet 保持顺序并去重，保留每个会话的最新记录
        return allRecords.stream()
                .map(AiChat::getSessionId)
                .distinct()  // 去重，保留第一次出现的 (即最新的)
                .collect(Collectors.toList());
    }

    @Override
    public void clearSession(Long userId, String sessionId) {
        this.remove(new LambdaQueryWrapper<AiChat>()
                .eq(AiChat::getUserId, userId)
                .eq(AiChat::getSessionId, sessionId));
    }
}
