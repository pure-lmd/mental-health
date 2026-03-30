package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.exception.BusinessException;
import com.ti.mental.common.result.ResultCode;
import com.ti.mental.entity.Feedback;
import com.ti.mental.mapper.FeedbackMapper;
import com.ti.mental.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 反馈Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public IPage<Feedback> page(Integer pageNum, Integer pageSize, Integer status) {
        Page<Feedback> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithUser(page, null, status);
    }

    @Override
    public void submit(Long userId, String title, String content, String images) {
        Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setImages(images);
        feedback.setStatus(Constants.FEEDBACK_STATUS_PENDING);
        this.save(feedback);
    }

    @Override
    public void reply(Long id, String reply) {
        Feedback feedback = this.getById(id);
        if (feedback == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        feedback.setReply(reply);
        feedback.setReplyTime(LocalDateTime.now());
        feedback.setStatus(Constants.FEEDBACK_STATUS_REPLIED);
        this.updateById(feedback);
    }

    @Override
    public IPage<Feedback> listByUserId(Long userId, Integer pageNum, Integer pageSize) {
        Page<Feedback> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithUser(page, userId, null);
    }

    @Override
    public void updateByUser(Long id, Long userId, String title, String content, String images) {
        Feedback feedback = this.getById(id);
        if (feedback == null || !feedback.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 已回复的反馈不能修改
        if (Constants.FEEDBACK_STATUS_REPLIED.equals(feedback.getStatus())) {
            throw new BusinessException("已回复的反馈不能修改");
        }
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setImages(images);
        this.updateById(feedback);
    }

    @Override
    public void deleteByUser(Long id, Long userId) {
        Feedback feedback = this.getById(id);
        if (feedback == null || !feedback.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        this.removeById(id);
    }
}
