package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.entity.LearningRecord;

/**
 * 学习记录服务接口
 *
 * @author Ti
 * @date 2026-04-06
 */
public interface LearningRecordService {

    /**
     * 记录用户学习行为
     *
     * @param userId      用户ID
     * @param contentType 内容类型(1科普/2宣传/3资讯/4活动)
     * @param contentId   内容ID
     * @param actionType  行为类型(1浏览/2收藏/3评论/4报名)
     * @param duration    浏览时长(秒)
     */
    void record(Long userId, Integer contentType, Long contentId, Integer actionType, Integer duration);

    /**
     * 获取用户学习记录
     *
     * @param userId    用户ID
     * @param pageNum   页码
     * @param pageSize  每页数量
     * @param contentType 内容类型(可选)
     * @return 分页结果
     */
    IPage<LearningRecord> getUserRecords(Long userId, Integer pageNum, Integer pageSize, Integer contentType);

    /**
     * 统计指定类型的行为数量
     *
     * @param userId      用户ID
     * @param contentType 内容类型
     * @param actionType  行为类型
     * @return 数量
     */
    Long countByType(Long userId, Integer contentType, Integer actionType);

    /**
     * 获取总学习时长
     *
     * @param userId 用户ID
     * @return 总时长(秒)
     */
    Integer getTotalDuration(Long userId);
}
