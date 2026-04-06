package com.ti.mental.controller.front;

import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.AiChat;
import com.ti.mental.service.AiChatService;
import com.ti.mental.service.EmotionAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AI聊天Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "AI助手")
@RestController
@RequestMapping("/api/front/ai")
public class AiChatController {

    @Resource
    private AiChatService aiChatService;

    @Resource
    private ChatClient.Builder chatClientBuilder;

    @Resource
    private EmotionAnalysisService emotionAnalysisService;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Operation(summary = "发送消息")
    @PostMapping("/chat")
    public Result<Map<String, Object>> chat(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        String sessionId = params.get("sessionId");
        String message = params.get("message");

        // 情绪分析
        emotionAnalysisService.analyzeAndWarn(userId, message);

        String reply = aiChatService.chat(userId, sessionId, message);

        Map<String, Object> result = new HashMap<>();
        result.put("reply", reply);
        result.put("sessionId", sessionId);
        return Result.success(result);
    }

    @Operation(summary = "流式发送消息（SSE）")
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatStream(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        String message = params.get("message");
        String sessionId = params.get("sessionId");

        // 情绪分析
        emotionAnalysisService.analyzeAndWarn(userId, message);

        SseEmitter emitter = new SseEmitter(120000L); // 2分钟超时

        executor.execute(() -> {
            StringBuilder fullReply = new StringBuilder();
            try {
                // 保存用户消息
                String finalSessionId = aiChatService.saveUserMessage(userId, sessionId, message);

                // 发送sessionId
                emitter.send(SseEmitter.event().name("sessionId").data(finalSessionId));

                // 构建消息列表
                List<Message> messages = aiChatService.buildMessages(userId, finalSessionId);

                // 流式调用AI
                ChatClient chatClient = chatClientBuilder.build();
                Flux<String> flux = chatClient.prompt()
                        .messages(messages)
                        .stream()
                        .content();

                flux.doOnNext(chunk -> {
                    try {
                        fullReply.append(chunk);
                        emitter.send(SseEmitter.event().name("message").data(chunk));
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                }).doOnComplete(() -> {
                    try {
                        // 保存完整的AI回复
                        aiChatService.saveAssistantMessage(userId, finalSessionId, fullReply.toString());
                        emitter.send(SseEmitter.event().name("done").data("[DONE]"));
                        emitter.complete();
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                }).doOnError(e -> {
                    try {
                        String errorReply = "抱歉，我现在有点忙，请稍后再试。";
                        aiChatService.saveAssistantMessage(userId, finalSessionId, errorReply);
                        emitter.send(SseEmitter.event().name("error").data(errorReply));
                        emitter.complete();
                    } catch (Exception ex) {
                        emitter.completeWithError(ex);
                    }
                }).subscribe();

            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().name("error").data("抱歉，发生了错误，请稍后再试。"));
                    emitter.complete();
                } catch (Exception ex) {
                    emitter.completeWithError(ex);
                }
            }
        });

        return emitter;
    }

    @Operation(summary = "获取对话历史")
    @GetMapping("/history")
    public Result<List<AiChat>> history(
            HttpServletRequest request,
            @RequestParam String sessionId) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        return Result.success(aiChatService.getHistory(userId, sessionId));
    }

    @Operation(summary = "获取会话列表")
    @GetMapping("/sessions")
    public Result<List<String>> sessions(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        return Result.success(aiChatService.getSessionIds(userId));
    }

    @Operation(summary = "清空会话")
    @DeleteMapping("/session/{sessionId}")
    public Result<Void> clearSession(HttpServletRequest request, @PathVariable String sessionId) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        aiChatService.clearSession(userId, sessionId);
        return Result.success();
    }
}
