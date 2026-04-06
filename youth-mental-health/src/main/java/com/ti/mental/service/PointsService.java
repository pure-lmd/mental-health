package com.ti.mental.service;

/**
 * 积分服务接口
 *
 * @author Ti
 * @date 2026-04-06
 */
public interface PointsService {

    /**
     * 完成测试奖励
     *
     * @param userId 用户ID
     * @param testId 测试ID
     */
    void rewardForTest(Long userId, Long testId);

    /**
     * 学习科普奖励
     *
     * @param userId      用户ID
     * @param knowledgeId 知识ID
     */
    void rewardForLearning(Long userId, Long knowledgeId);

    /**
     * 参加活动奖励
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     */
    void rewardForActivity(Long userId, Long activityId);

    /**
     * 每日签到奖励
     *
     * @param userId 用户ID
     */
    void rewardForSignIn(Long userId);

    /**
     * 获取用户总积分
     *
     * @param userId 用户ID
     * @return 积分
     */
    Integer getUserPoints(Long userId);

    /**
     * 获取用户累计获得积分
     *
     * @param userId 用户ID
     * @return 累计积分
     */
    Integer getTotalEarnedPoints(Long userId);
}
