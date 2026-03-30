package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.entity.Option;
import com.ti.mental.entity.Question;
import com.ti.mental.mapper.OptionMapper;
import com.ti.mental.mapper.QuestionMapper;
import com.ti.mental.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试题Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Resource
    private OptionMapper optionMapper;

    @Override
    public List<Question> listByTestId(Long testId) {
        List<Question> questions = this.lambdaQuery()
                .eq(Question::getTestId, testId)
                .orderByAsc(Question::getSort)
                .list();
        // 查询每道题的选项
        questions.forEach(question -> {
            List<Option> options = optionMapper.selectList(
                    new LambdaQueryWrapper<Option>()
                            .eq(Option::getQuestionId, question.getId())
                            .orderByAsc(Option::getSort));
            question.setOptions(options);
        });
        return questions;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addWithOptions(Question question) {
        // 保存题目
        this.save(question);
        // 保存选项
        if (question.getOptions() != null && !question.getOptions().isEmpty()) {
            question.getOptions().forEach(option -> {
                option.setQuestionId(question.getId());
                optionMapper.insert(option);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWithOptions(Question question) {
        // 更新题目
        this.updateById(question);
        // 删除旧选项
        optionMapper.delete(new LambdaQueryWrapper<Option>()
                .eq(Option::getQuestionId, question.getId()));
        // 保存新选项
        if (question.getOptions() != null && !question.getOptions().isEmpty()) {
            question.getOptions().forEach(option -> {
                option.setId(null);
                option.setQuestionId(question.getId());
                optionMapper.insert(option);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWithOptions(Long id) {
        // 删除题目
        this.removeById(id);
        // 删除选项
        optionMapper.delete(new LambdaQueryWrapper<Option>()
                .eq(Option::getQuestionId, id));
    }
}
