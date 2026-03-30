package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.Comment;
import com.ti.mental.mapper.CommentMapper;
import com.ti.mental.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论 Service 实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public IPage<Comment> page(Integer pageNum, Integer pageSize, Integer type, Integer status) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectAdminPage(page, type, status);
    }

    @Override
    public IPage<Comment> listByTarget(Long targetId, Integer type, Integer pageNum, Integer pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        IPage<Comment> result = baseMapper.selectPageWithUser(page, targetId, type, Constants.COMMENT_STATUS_APPROVED);
        // 加载子评论
        result.getRecords().forEach(comment -> {
            List<Comment> children = baseMapper.selectChildren(comment.getId());
            comment.setChildren(children);
        });
        return result;
    }

    @Override
    public void add(Long userId, Long targetId, Integer type, String content, Long parentId) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setTargetId(targetId);
        comment.setType(type);
        comment.setContent(content);
        comment.setParentId(parentId != null ? parentId : 0L);
        comment.setStatus(Constants.COMMENT_STATUS_PENDING); // 新评论默认为待审核状态
        this.save(comment);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(status);
        this.updateById(comment);
    }

    @Override
    public void audit(Long id, Integer status, String auditRemark) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(status);
        comment.setAuditRemark(auditRemark);
        comment.setAuditTime(LocalDateTime.now());
        this.updateById(comment);
    }
}
