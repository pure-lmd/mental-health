package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Registration;

/**
 * 活动报名Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface RegistrationService extends IService<Registration> {

    /**
     * 分页查询报名记录（管理端）
     *
     * @param pageNum    页码
     * @param pageSize   每页数量
     * @param activityId 活动ID
     * @param status     状态
     * @return 分页结果
     */
    IPage<Registration> page(Integer pageNum, Integer pageSize, Long activityId, Integer status);

    /**
     * 用户报名活动
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @param remark     备注
     */
    void register(Long userId, Long activityId, String remark);

    /**
     * 审核报名
     *
     * @param id          报名ID
     * @param status      状态
     * @param auditRemark 审核备注
     */
    void audit(Long id, Integer status, String auditRemark);

    /**
     * 取消报名
     *
     * @param id     报名ID
     * @param userId 用户ID
     */
    void cancel(Long id, Long userId);

    /**
     * 获取用户的报名记录
     *
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<Registration> listByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 检查用户是否已报名
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return 是否已报名
     */
    boolean hasRegistered(Long userId, Long activityId);
}
