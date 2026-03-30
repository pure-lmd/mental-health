package com.ti.mental.service;

import java.util.Map;

/**
 * 数据统计Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface StatisticsService {

    /**
     * 获取概览数据
     *
     * @return 概览数据
     */
    Map<String, Object> getOverview();

    /**
     * 获取测试统计
     *
     * @return 测试统计
     */
    Map<String, Object> getTestStatistics();

    /**
     * 获取健康状态统计
     *
     * @return 健康状态统计
     */
    Map<String, Object> getHealthStatistics();

    /**
     * 获取活动统计
     *
     * @return 活动统计
     */
    Map<String, Object> getActivityStatistics();
}
