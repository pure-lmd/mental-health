package com.ti.mental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Question;

import java.util.List;

/**
 * 测试题Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface QuestionService extends IService<Question> {

    /**
     * 获取测试卷的所有题目（含选项）
     *
     * @param testId 测试卷ID
     * @return 题目列表
     */
    List<Question> listByTestId(Long testId);

    /**
     * 添加题目（含选项）
     *
     * @param question 题目信息
     */
    void addWithOptions(Question question);

    /**
     * 更新题目（含选项）
     *
     * @param question 题目信息
     */
    void updateWithOptions(Question question);

    /**
     * 删除题目（含选项）
     *
     * @param id 题目ID
     */
    void deleteWithOptions(Long id);
}
