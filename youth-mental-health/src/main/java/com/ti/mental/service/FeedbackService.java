package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Feedback;

/**
 * 反馈Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface FeedbackService extends IService<Feedback> {

    /**
     * 分页查询反馈（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param status   状态
     * @return 分页结果
     */
    IPage<Feedback> page(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 提交反馈
     *
     * @param userId  用户ID
     * @param title   标题
     * @param content 内容
     * @param images  图片
     */
    void submit(Long userId, String title, String content, String images);

    /**
     * 回复反馈
     *
     * @param id    反馈ID
     * @param reply 回复内容
     */
    void reply(Long id, String reply);

    /**
     * 获取用户的反馈列表
     *
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<Feedback> listByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 用户更新反馈
     *
     * @param id      反馈ID
     * @param userId  用户ID
     * @param title   标题
     * @param content 内容
     * @param images  图片
     */
    void updateByUser(Long id, Long userId, String title, String content, String images);

    /**
     * 用户删除反馈
     *
     * @param id     反馈ID
     * @param userId 用户ID
     */
    void deleteByUser(Long id, Long userId);
}
