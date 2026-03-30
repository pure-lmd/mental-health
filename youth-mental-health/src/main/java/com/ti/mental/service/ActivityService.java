package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Activity;

import java.util.List;

/**
 * 活动Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface ActivityService extends IService<Activity> {

    /**
     * 分页查询活动（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param title    标题
     * @param status   状态
     * @return 分页结果
     */
    IPage<Activity> page(Integer pageNum, Integer pageSize, String title, Integer status);

    /**
     * 分页查询活动（用户端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<Activity> listEnabled(Integer pageNum, Integer pageSize);

    /**
     * 获取热门活动
     *
     * @param limit 数量限制
     * @return 活动列表
     */
    List<Activity> listHot(Integer limit);

    /**
     * 增加报名人数
     *
     * @param id 活动ID
     */
    void incrCurrentNum(Long id);

    /**
     * 减少报名人数
     *
     * @param id 活动ID
     */
    void decrCurrentNum(Long id);

    /**
     * 获取活动报名统计
     *
     * @return 活动报名统计列表
     */
    List<java.util.Map<String, Object>> getActivityRegistrationStats();
}
