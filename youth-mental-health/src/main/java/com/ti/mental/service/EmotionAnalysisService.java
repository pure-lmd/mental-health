package com.ti.mental.service;

/**
 * 情绪分析服务接口
 *
 * @author Ti
 * @date 2026-04-06
 */
public interface EmotionAnalysisService {

    /**
     * 分析用户消息并创建预警（如果检测到高风险）
     *
     * @param userId  用户ID
     * @param message 用户消息
     */
    void analyzeAndWarn(Long userId, String message);
}
