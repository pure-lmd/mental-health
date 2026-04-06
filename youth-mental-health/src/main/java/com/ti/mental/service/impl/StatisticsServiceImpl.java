package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ti.mental.entity.Activity;
import com.ti.mental.entity.Knowledge;
import com.ti.mental.entity.TestRecord;
import com.ti.mental.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        List<String> scoreRanges = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

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
                .eq(Activity::getStatus, 1)
                .count());
        // 进行中的活动
        result.put("ongoingCount", activityService.lambdaQuery()
                .eq(Activity::getStatus, 2)
                .count());
        // 已结束的活动
        result.put("finishedCount", activityService.lambdaQuery()
                .eq(Activity::getStatus, 3)
                .count());

        // 获取各活动的报名统计
        List<Map<String, Object>> activityStats = activityService.getActivityRegistrationStats();
        result.put("activityStats", activityStats);

        // 提取活动名称和报名人数数组（用于图表）
        List<String> activityNames = new ArrayList<>();
        List<Long> registrationCounts = new ArrayList<>();
        for (Map<String, Object> stat : activityStats) {
            activityNames.add((String) stat.get("title"));
            registrationCounts.add(((Number) stat.get("registrationCount")).longValue());
        }
        result.put("activityNames", activityNames);
        result.put("registrationCounts", registrationCounts);

        return result;
    }

    @Override
    public Map<String, Object> getHealthTrend() {
        Map<String, Object> result = new HashMap<>();

        // 获取近7天的测试记录趋势
        List<String> dates = new ArrayList<>();
        List<Long> testCounts = new ArrayList<>();
        List<Long> needTalkCounts = new ArrayList<>();

        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            dates.add(date.format(DATE_FORMATTER));

            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

            // 当天测试总数
            long dayTotal = testRecordService.lambdaQuery()
                    .ge(TestRecord::getCreateTime, startOfDay)
                    .lt(TestRecord::getCreateTime, endOfDay)
                    .count();
            testCounts.add(dayTotal);

            // 当天建议沟通人数
            long dayNeedTalk = testRecordService.lambdaQuery()
                    .ge(TestRecord::getCreateTime, startOfDay)
                    .lt(TestRecord::getCreateTime, endOfDay)
                    .eq(TestRecord::getResult, 2)
                    .count();
            needTalkCounts.add(dayNeedTalk);
        }

        result.put("dates", dates);
        result.put("testCounts", testCounts);
        result.put("needTalkCounts", needTalkCounts);

        return result;
    }

    @Override
    public Map<String, Object> getActivityEffect() {
        Map<String, Object> result = new HashMap<>();

        // 活动总数
        long totalActivities = activityService.count();
        result.put("totalActivities", totalActivities);

        // 总报名人次
        long totalRegistrations = registrationService.count();
        result.put("totalRegistrations", totalRegistrations);

        // 平均每个活动报名人数
        double avgRegistrations = totalActivities > 0 ? (double) totalRegistrations / totalActivities : 0;
        result.put("avgRegistrations", Math.round(avgRegistrations * 100) / 100.0);

        // 热门活动TOP5
        List<Map<String, Object>> activityStats = activityService.getActivityRegistrationStats();
        List<Map<String, Object>> topActivities = activityStats.stream()
                .sorted((a, b) -> ((Long) b.get("registrationCount")).compareTo((Long) a.get("registrationCount")))
                .limit(5)
                .collect(Collectors.toList());
        result.put("topActivities", topActivities);

        // 活动状态分布
        Map<String, Long> statusDistribution = new HashMap<>();
        statusDistribution.put("报名中", activityService.lambdaQuery().eq(Activity::getStatus, 1).count());
        statusDistribution.put("进行中", activityService.lambdaQuery().eq(Activity::getStatus, 2).count());
        statusDistribution.put("已结束", activityService.lambdaQuery().eq(Activity::getStatus, 3).count());
        result.put("statusDistribution", statusDistribution);

        return result;
    }

    @Override
    public Map<String, Object> getKnowledgeEffect() {
        Map<String, Object> result = new HashMap<>();

        // 科普总数
        long totalKnowledge = knowledgeService.count();
        result.put("totalKnowledge", totalKnowledge);

        // 总浏览量
        List<Knowledge> allKnowledge = knowledgeService.list();
        long totalViews = allKnowledge.stream()
                .mapToLong(k -> k.getViewCount() != null ? k.getViewCount() : 0)
                .sum();
        result.put("totalViews", totalViews);

        // 平均浏览量
        double avgViews = totalKnowledge > 0 ? (double) totalViews / totalKnowledge : 0;
        result.put("avgViews", Math.round(avgViews * 100) / 100.0);

        // 热门科普TOP5
        List<Map<String, Object>> topKnowledge = allKnowledge.stream()
                .sorted((a, b) -> {
                    int viewA = a.getViewCount() != null ? a.getViewCount() : 0;
                    int viewB = b.getViewCount() != null ? b.getViewCount() : 0;
                    return Integer.compare(viewB, viewA);
                })
                .limit(5)
                .map(k -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", k.getId());
                    map.put("title", k.getTitle());
                    map.put("viewCount", k.getViewCount());
                    map.put("collectCount", k.getCollectCount());
                    return map;
                })
                .collect(Collectors.toList());
        result.put("topKnowledge", topKnowledge);

        return result;
    }
}
