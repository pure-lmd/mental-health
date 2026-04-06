package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ti.mental.entity.PointsRecord;
import com.ti.mental.entity.User;
import com.ti.mental.mapper.PointsRecordMapper;
import com.ti.mental.mapper.UserMapper;
import com.ti.mental.service.PointsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 积分服务实现
 *
 * @author Ti
 * @date 2026-04-06
 */
@Service
public class PointsServiceImpl implements PointsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PointsRecordMapper pointsRecordMapper;

    private static final int POINTS_FOR_TEST = 10;
    private static final int POINTS_FOR_LEARNING = 5;
    private static final int POINTS_FOR_ACTIVITY = 20;
    private static final int POINTS_FOR_SIGN_IN = 2;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rewardForTest(Long userId, Long testId) {
        addPoints(userId, POINTS_FOR_TEST, 1, testId, "完成心理测试");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rewardForLearning(Long userId, Long knowledgeId) {
        addPoints(userId, POINTS_FOR_LEARNING, 2, knowledgeId, "学习科普文章");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rewardForActivity(Long userId, Long activityId) {
        addPoints(userId, POINTS_FOR_ACTIVITY, 3, activityId, "参加心理活动");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rewardForSignIn(Long userId) {
        addPoints(userId, POINTS_FOR_SIGN_IN, 4, null, "每日签到");
    }

    @Override
    public Integer getUserPoints(Long userId) {
        User user = userMapper.selectById(userId);
        return user != null ? user.getPoints() : 0;
    }

    @Override
    public Integer getTotalEarnedPoints(Long userId) {
        LambdaQueryWrapper<PointsRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PointsRecord::getUserId, userId)
                .gt(PointsRecord::getPoints, 0)
                .select(PointsRecord::getPoints);

        return pointsRecordMapper.selectList(wrapper).stream()
                .mapToInt(PointsRecord::getPoints)
                .sum();
    }

    /**
     * 增加积分
     */
    private void addPoints(Long userId, int points, int sourceType, Long sourceId, String description) {
        // 更新用户积分
        User user = userMapper.selectById(userId);
        if (user == null) {
            return;
        }

        user.setPoints(user.getPoints() != null ? user.getPoints() + points : points);
        userMapper.updateById(user);

        // 记录积分变化
        PointsRecord record = new PointsRecord();
        record.setUserId(userId);
        record.setPoints(points);
        record.setSourceType(sourceType);
        record.setSourceId(sourceId);
        record.setDescription(description);
        pointsRecordMapper.insert(record);
    }
}
