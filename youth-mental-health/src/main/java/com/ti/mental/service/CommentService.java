package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Comment;

/**
 * 评论 Service 接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface CommentService extends IService<Comment> {

    /**
     * 分页查询评论（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param type     类型
     * @param status   状态
     * @return 分页结果
     */
    IPage<Comment> page(Integer pageNum, Integer pageSize, Integer type, Integer status);

    /**
     * 获取目标的评论列表（用户端）
     *
     * @param targetId 目标 ID
     * @param type     类型
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果（含子评论）
     */
    IPage<Comment> listByTarget(Long targetId, Integer type, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     *
     * @param userId   用户 ID
     * @param targetId 目标 ID
     * @param type     类型
     * @param content  内容
     * @param parentId 父评论 ID
     */
    void add(Long userId, Long targetId, Integer type, String content, Long parentId);

    /**
     * 更新评论状态
     *
     * @param id     评论 ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 审核评论
     *
     * @param id          评论 ID
     * @param status      审核状态（0 待审核/1 已通过/2 已拒绝）
     * @param auditRemark 审核备注
     */
    void audit(Long id, Integer status, String auditRemark);
}
