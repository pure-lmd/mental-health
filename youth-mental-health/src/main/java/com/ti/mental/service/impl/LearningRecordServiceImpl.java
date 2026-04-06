package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ti.mental.entity.LearningRecord;
import com.ti.mental.mapper.LearningRecordMapper;
import com.ti.mental.service.LearningRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 学习记录服务实现
 *
 * @author Ti
 * @date 2026-04-06
 */
@Service
public class LearningRecordServiceImpl implements LearningRecordService {

    @Resource
    private LearningRecordMapper learningRecordMapper;

    @Override
    public void record(Long userId, Integer contentType, Long contentId, Integer actionType, Integer duration) {
        LearningRecord record = new LearningRecord();
        record.setUserId(userId);
        record.setContentType(contentType);
        record.setContentId(contentId);
        record.setActionType(actionType);
        record.setDuration(duration != null ? duration : 0);
        learningRecordMapper.insert(record);
    }

    @Override
    public IPage<LearningRecord> getUserRecords(Long userId, Integer pageNum, Integer pageSize, Integer contentType) {
        Page<LearningRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<LearningRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LearningRecord::getUserId, userId)
                .eq(contentType != null, LearningRecord::getContentType, contentType)
                .orderByDesc(LearningRecord::getCreateTime);
        return learningRecordMapper.selectPage(page, wrapper);
    }

    @Override
    public Long countByType(Long userId, Integer contentType, Integer actionType) {
        LambdaQueryWrapper<LearningRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LearningRecord::getUserId, userId)
                .eq(LearningRecord::getContentType, contentType)
                .eq(LearningRecord::getActionType, actionType);
        return learningRecordMapper.selectCount(wrapper);
    }

    @Override
    public Integer getTotalDuration(Long userId) {
        LambdaQueryWrapper<LearningRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LearningRecord::getUserId, userId)
                .select(LearningRecord::getDuration);
        
        return learningRecordMapper.selectList(wrapper).stream()
                .mapToInt(r -> r.getDuration() != null ? r.getDuration() : 0)
                .sum();
    }
}
