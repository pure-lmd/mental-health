package com.ti.mental.service;

import com.ti.mental.dto.RecommendVO;

/**
 * 推荐服务接口
 *
 * @author Ti
 * @date 2026-04-06
 */
public interface RecommendService {

    /**
     * 根据测试结果生成个性化推荐
     *
     * @param userId     用户ID
     * @param healthStatus 健康状态(0健康/1良好/2建议沟通)
     * @return 推荐结果
     */
    RecommendVO generateRecommend(Long userId, Integer healthStatus);

    /**
     * 记录推荐点击
     *
     * @param userId        用户ID
     * @param recommendType 推荐类型
     * @param contentId     内容ID
     */
    void recordClick(Long userId, Integer recommendType, Long contentId);
}
