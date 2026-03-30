package com.ti.mental.service.impl;

import com.ti.mental.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private UserService userService;

    @Resource
    private PsyTestService psyTestService;

    @Resource
    private TestRecordService testRecordService;

    @Resource
    private ActivityService activityService;

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private RegistrationService registrationService;

    @Override
    public Map<String, Object> getOverview() {
        Map<String, Object> result = new HashMap<>();
        // 用户总数
        result.put("userCount", userService.count());
        // 测试卷数量
        result.put("testCount", psyTestService.count());
        // 测试记录数量
        result.put("testRecordCount", testRecordService.count());
        // 活动数量
        result.put("activityCount", activityService.count());
        // 报名人次
        result.put("registrationCount", registrationService.count());
        // 科普数量
        result.put("knowledgeCount", knowledgeService.count());
        // 待回复反馈数量
        result.put("pendingFeedbackCount", feedbackService.lambdaQuery()
                .eq(com.ti.mental.entity.Feedback::getStatus, 0)
                .count());
        return result;
    }

    @Override
    public Map<String, Object> getTestStatistics() {
        Map<String, Object> result = new HashMap<>();
        // 测试记录总数
        result.put("totalRecords", testRecordService.count());
        
        // 获取分数段统计
        List<Map<String, Object>> scoreStats = testRecordService.getScoreRangeStats();
        
        // 转换为前端友好的格式
        java.util.List<String> scoreRanges = new java.util.ArrayList<>();
        java.util.List<Long> counts = new java.util.ArrayList<>();
        
        for (Map<String, Object> stat : scoreStats) {
            scoreRanges.add((String) stat.get("scoreRange"));
            counts.add(((Number) stat.get("count")).longValue());
        }
        
        result.put("scoreRanges", scoreRanges);
        result.put("counts", counts);
        
        return result;
    }

    @Override
    public Map<String, Object> getHealthStatistics() {
        Map<String, Object> result = new HashMap<>();
        // 各健康状态人数统计
        List<Map<String, Object>> statusCounts = userService.countByHealthStatus();
        result.put("statusDistribution", statusCounts);
        
        // 转换为前端友好的格式
        long healthCount = 0, goodCount = 0, needTalkCount = 0;
        for (Map<String, Object> item : statusCounts) {
            Integer status = ((Number) item.get("health_status")).intValue();
            Long count = ((Number) item.get("count")).longValue();
            switch (status) {
                case 0 -> healthCount = count;
                case 1 -> goodCount = count;
                case 2 -> needTalkCount = count;
            }
        }
        result.put("healthCount", healthCount);
        result.put("goodCount", goodCount);
        result.put("needTalkCount", needTalkCount);
        result.put("totalCount", healthCount + goodCount + needTalkCount);
        
        return result;
    }

    @Override
    public Map<String, Object> getActivityStatistics() {
        Map<String, Object> result = new HashMap<>();
        // 活动总数
        result.put("totalActivities", activityService.count());
        // 报名中的活动
        result.put("registeringCount", activityService.lambdaQuery()
                .eq(com.ti.mental.entity.Activity::getStatus, 1)
                .count());
        // 进行中的活动
        result.put("ongoingCount", activityService.lambdaQuery()
                .eq(com.ti.mental.entity.Activity::getStatus, 2)
                .count());
        // 已结束的活动
        result.put("finishedCount", activityService.lambdaQuery()
                .eq(com.ti.mental.entity.Activity::getStatus, 3)
                .count());
        
        // 获取各活动的报名统计
        List<Map<String, Object>> activityStats = activityService.getActivityRegistrationStats();
        result.put("activityStats", activityStats);
        
        // 提取活动名称和报名人数数组（用于图表）
        java.util.List<String> activityNames = new java.util.ArrayList<>();
        java.util.List<Long> registrationCounts = new java.util.ArrayList<>();
        for (Map<String, Object> stat : activityStats) {
            activityNames.add((String) stat.get("title"));
            registrationCounts.add(((Number) stat.get("registrationCount")).longValue());
        }
        result.put("activityNames", activityNames);
        result.put("registrationCounts", registrationCounts);
        
        return result;
    }
}
