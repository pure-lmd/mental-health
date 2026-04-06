package com.ti.mental.service.impl;

import com.ti.mental.service.EmotionAnalysisService;
import com.ti.mental.service.WarningService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 情绪分析服务实现
 *
 * @author Ti
 * @date 2026-04-06
 */
@Slf4j
@Service
public class EmotionAnalysisServiceImpl implements EmotionAnalysisService {

    @Resource
    private WarningService warningService;

    // 高风险关键词库
    private static final List<String> HIGH_RISK_KEYWORDS = Arrays.asList(
            "自杀", "不想活", "去死", "结束生命", "跳楼", "割腕",
            "绝望", "崩溃", "活不下去", "没意思", "活着没意义"
    );

    // 中风险关键词库
    private static final List<String> MEDIUM_RISK_KEYWORDS = Arrays.asList(
            "抑郁", "焦虑", "痛苦", "无助", "孤独", "没人理解",
            "压力大", "失眠", "哭泣", "难过", "伤心", "害怕",
            "紧张", "恐惧", "担心", "烦躁", "愤怒", "生气"
    );

    @Override
    public void analyzeAndWarn(Long userId, String message) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }

        // 转换为小写进行匹配
        String lowerMessage = message.toLowerCase();

        // 检查高风险关键词
        List<String> matchedHighRisk = HIGH_RISK_KEYWORDS.stream()
                .filter(lowerMessage::contains)
                .collect(Collectors.toList());

        if (!matchedHighRisk.isEmpty()) {
            // 高风险：立即创建预警
            String keywords = String.join(", ", matchedHighRisk);
            warningService.createWarning(userId, "HIGH", keywords);
            log.warn("检测到高风险用户 [userId={}], 关键词: {}", userId, keywords);
            return;
        }

        // 检查中风险关键词
        List<String> matchedMediumRisk = MEDIUM_RISK_KEYWORDS.stream()
                .filter(lowerMessage::contains)
                .collect(Collectors.toList());

        if (matchedMediumRisk.size() >= 2) {
            // 中风险：匹配到2个及以上中风险关键词才预警
            String keywords = String.join(", ", matchedMediumRisk);
            warningService.createWarning(userId, "MEDIUM", keywords);
            log.warn("检测到中风险用户 [userId={}], 关键词: {}", userId, keywords);
        }
    }
}
