package com.ti.mental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.AiChat;

import java.util.List;

/**
 * AI聊天Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface AiChatService extends IService<AiChat> {

    /**
     * 发送消息并获取回复
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @param message   用户消息
     * @return AI回复
     */
    String chat(Long userId, String sessionId, String message);

    /**
     * 保存用户消息并构建AI消息列表
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @param message   用户消息
     * @return 会话ID（可能新生成）
     */
    String saveUserMessage(Long userId, String sessionId, String message);

    /**
     * 获取构建好的消息列表用于AI调用
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @return 消息列表
     */
    List<org.springframework.ai.chat.messages.Message> buildMessages(Long userId, String sessionId);

    /**
     * 保存AI回复消息
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @param content   回复内容
     */
    void saveAssistantMessage(Long userId, String sessionId, String content);

    /**
     * 获取对话历史
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @return 对话列表
     */
    List<AiChat> getHistory(Long userId, String sessionId);

    /**
     * 获取用户的所有会话ID
     *
     * @param userId 用户ID
     * @return 会话ID列表
     */
    List<String> getSessionIds(Long userId);

    /**
     * 清空会话
     *
     * @param userId    用户ID
     * @param sessionId 会话ID
     */
    void clearSession(Long userId, String sessionId);
}
