package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.PsyTest;

import java.util.List;
import java.util.Map;

/**
 * 测试卷Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface PsyTestService extends IService<PsyTest> {

    /**
     * 分页查询测试卷（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param title    标题
     * @return 分页结果
     */
    IPage<PsyTest> page(Integer pageNum, Integer pageSize, String title);

    /**
     * 获取测试卷列表（用户端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<PsyTest> listEnabled(Integer pageNum, Integer pageSize);

    /**
     * 获取测试卷详情（含题目和选项）
     *
     * @param id 测试卷ID
     * @return 测试卷详情
     */
    PsyTest getDetailWithQuestions(Long id);

    /**
     * 提交测试并计算结果
     *
     * @param userId  用户ID
     * @param testId  测试卷ID
     * @param answers 答案列表 [{questionId, optionId}]
     * @return 测试结果
     */
    Map<String, Object> submitTest(Long userId, Long testId, List<Map<String, Object>> answers);
}
