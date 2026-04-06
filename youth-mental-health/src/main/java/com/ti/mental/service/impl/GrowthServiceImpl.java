package com.ti.mental.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.dto.GrowthTrajectoryVO;
import com.ti.mental.dto.LearningStatsVO;
import com.ti.mental.entity.PsyTest;
import com.ti.mental.entity.TestRecord;
import com.ti.mental.entity.User;
import com.ti.mental.mapper.AiChatMapper;
import com.ti.mental.mapper.PsyTestMapper;
import com.ti.mental.mapper.TestRecordMapper;
import com.ti.mental.mapper.UserMapper;
import com.ti.mental.service.AiChatService;
import com.ti.mental.service.GrowthService;
import com.ti.mental.service.LearningRecordService;
import com.ti.mental.service.PointsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 成长轨迹服务实现
 *
 * @author Ti
 * @date 2026-04-06
 */
@Service
public class GrowthServiceImpl implements GrowthService {

    @Resource
    private TestRecordMapper testRecordMapper;

    @Resource
    private PsyTestMapper psyTestMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private LearningRecordService learningRecordService;

    @Resource
    private PointsService pointsService;

    @Resource
    private AiChatMapper aiChatMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public GrowthTrajectoryVO getTrajectory(Long userId) {
        GrowthTrajectoryVO trajectory = new GrowthTrajectoryVO();

        // 1. 获取测试成绩变化趋势
        List<TestRecord> records = getTestRecords(userId);
        trajectory.setTestTrend(buildTestTrend(records));

        // 2. 获取学习行为统计
        LearningStatsVO stats = getLearningStats(userId);
        trajectory.setLearningStats(stats);

        // 3. 获取健康状态变化
        trajectory.setHealthStatusHistory(buildHealthStatusHistory(records));

        return trajectory;
    }

    @Override
    public LearningStatsVO getLearningStats(Long userId) {
        LearningStatsVO stats = new LearningStatsVO();

        // 科普浏览/收藏数
        stats.setKnowledgeViewCount(learningRecordService.countByType(userId, 1, 1).intValue());
        stats.setKnowledgeCollectCount(learningRecordService.countByType(userId, 1, 2).intValue());

        // 活动报名数
        stats.setActivityJoinCount(learningRecordService.countByType(userId, 4, 4).intValue());

        // AI咨询次数
        stats.setAiChatCount(getAiChatCount(userId).intValue());

        // 总学习时长
        stats.setTotalDuration(learningRecordService.getTotalDuration(userId));

        // 当前积分
        stats.setCurrentPoints(pointsService.getUserPoints(userId));

        // 累计获得积分
        stats.setTotalEarnedPoints(pointsService.getTotalEarnedPoints(userId));

        return stats;
    }

    /**
     * 获取用户测试记录
     */
    private List<TestRecord> getTestRecords(Long userId) {
        LambdaQueryWrapper<TestRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TestRecord::getUserId, userId)
                .orderByAsc(TestRecord::getCreateTime);
        return testRecordMapper.selectList(wrapper);
    }

    /**
     * 构建测试趋势
     */
    private List<GrowthTrajectoryVO.TestTrendItem> buildTestTrend(List<TestRecord> records) {
        if (CollUtil.isEmpty(records)) {
            return new ArrayList<>();
        }

        return records.stream().map(record -> {
            GrowthTrajectoryVO.TestTrendItem item = new GrowthTrajectoryVO.TestTrendItem();
            item.setTestDate(record.getCreateTime().format(DATE_FORMATTER));
            item.setScore(record.getTotalScore());
            item.setResult(record.getResult());

            // 获取测试标题
            if (record.getTestId() != null) {
                PsyTest test = psyTestMapper.selectById(record.getTestId());
                if (test != null) {
                    item.setTestTitle(test.getTitle());
                }
            }

            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 构建健康状态历史
     */
    private List<GrowthTrajectoryVO.HealthStatusItem> buildHealthStatusHistory(List<TestRecord> records) {
        if (CollUtil.isEmpty(records)) {
            return new ArrayList<>();
        }

        return records.stream().map(record -> {
            GrowthTrajectoryVO.HealthStatusItem item = new GrowthTrajectoryVO.HealthStatusItem();
            item.setDate(record.getCreateTime().format(DATE_FORMATTER));
            item.setHealthStatus(record.getResult());
            item.setStatusText(getResultText(record.getResult()));
            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 获取结果文本
     */
    private String getResultText(Integer result) {
        if (result == null) {
            return "未知";
        }
        if (Constants.HEALTH_STATUS_HEALTHY.equals(result)) {
            return "健康";
        } else if (Constants.HEALTH_STATUS_GOOD.equals(result)) {
            return "良好";
        } else if (Constants.HEALTH_STATUS_NEED_TALK.equals(result)) {
            return "建议沟通";
        } else {
            return "未知";
        }
    }

    /**
     * 获取AI咨询次数
     */
    private Long getAiChatCount(Long userId) {
        // 统计不同的会话数量
        LambdaQueryWrapper<com.ti.mental.entity.AiChat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(com.ti.mental.entity.AiChat::getUserId, userId)
                .eq(com.ti.mental.entity.AiChat::getRole, "user")
                .select(com.ti.mental.entity.AiChat::getSessionId);
        
        List<com.ti.mental.entity.AiChat> chats = aiChatMapper.selectList(wrapper);
        return chats.stream()
                .map(com.ti.mental.entity.AiChat::getSessionId)
                .distinct()
                .count();
    }
}
