package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.entity.TestRecord;
import com.ti.mental.mapper.TestRecordMapper;
import com.ti.mental.service.TestRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 测试记录Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class TestRecordServiceImpl extends ServiceImpl<TestRecordMapper, TestRecord> implements TestRecordService {

    @Override
    public IPage<TestRecord> page(Integer pageNum, Integer pageSize, Long userId, Long testId) {
        Page<TestRecord> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithInfo(page, userId, testId);
    }

    @Override
    public IPage<TestRecord> listByUserId(Long userId, Integer pageNum, Integer pageSize) {
        Page<TestRecord> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithInfo(page, userId, null);
    }

    @Override
    public List<Map<String, Object>> countByResult() {
        return baseMapper.countByResult();
    }

    @Override
    public List<Map<String, Object>> getScoreRangeStats() {
        return baseMapper.getScoreRangeStats();
    }
}
