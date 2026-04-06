package com.ti.mental.service;

import com.ti.mental.dto.GrowthTrajectoryVO;
import com.ti.mental.dto.LearningStatsVO;

/**
 * 成长轨迹服务接口
 *
 * @author Ti
 * @date 2026-04-06
 */
public interface GrowthService {

    /**
     * 获取用户成长轨迹
     *
     * @param userId 用户ID
     * @return 成长轨迹
     */
    GrowthTrajectoryVO getTrajectory(Long userId);

    /**
     * 获取学习统计
     *
     * @param userId 用户ID
     * @return 学习统计
     */
    LearningStatsVO getLearningStats(Long userId);
}
