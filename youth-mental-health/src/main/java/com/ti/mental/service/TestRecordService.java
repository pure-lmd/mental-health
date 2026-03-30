package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.TestRecord;

import java.util.List;
import java.util.Map;

/**
 * 测试记录Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface TestRecordService extends IService<TestRecord> {

    /**
     * 分页查询测试记录（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param userId   用户ID
     * @param testId   测试卷ID
     * @return 分页结果
     */
    IPage<TestRecord> page(Integer pageNum, Integer pageSize, Long userId, Long testId);

    /**
     * 获取用户的测试记录
     *
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<TestRecord> listByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 统计各结果人数
     *
     * @return 统计结果
     */
    List<Map<String, Object>> countByResult();

    /**
     * 获取分数段统计
     *
     * @return 分数段统计结果
     */
    List<Map<String, Object>> getScoreRangeStats();
}
