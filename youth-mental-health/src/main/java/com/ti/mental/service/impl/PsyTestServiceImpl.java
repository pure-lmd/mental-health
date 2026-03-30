package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.Option;
import com.ti.mental.entity.PsyTest;
import com.ti.mental.entity.Question;
import com.ti.mental.entity.TestRecord;
import com.ti.mental.mapper.OptionMapper;
import com.ti.mental.mapper.PsyTestMapper;
import com.ti.mental.mapper.QuestionMapper;
import com.ti.mental.mapper.TestRecordMapper;
import com.ti.mental.service.PsyTestService;
import com.ti.mental.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试卷Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class PsyTestServiceImpl extends ServiceImpl<PsyTestMapper, PsyTest> implements PsyTestService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private OptionMapper optionMapper;

    @Resource
    private TestRecordMapper testRecordMapper;

    @Resource
    private UserService userService;

    @Override
    public IPage<PsyTest> page(Integer pageNum, Integer pageSize, String title) {
        Page<PsyTest> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PsyTest> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(title), PsyTest::getTitle, title)
                .orderByAsc(PsyTest::getSort)
                .orderByDesc(PsyTest::getCreateTime);
        IPage<PsyTest> result = this.page(page, wrapper);
        // 查询题目数量
        result.getRecords().forEach(test -> {
            long count = questionMapper.selectCount(
                    new LambdaQueryWrapper<Question>().eq(Question::getTestId, test.getId()));
            test.setQuestionCount((int) count);
        });
        return result;
    }

    @Override
    public IPage<PsyTest> listEnabled(Integer pageNum, Integer pageSize) {
        Page<PsyTest> page = new Page<>(pageNum, pageSize);
        IPage<PsyTest> result = this.lambdaQuery()
                .eq(PsyTest::getStatus, Constants.STATUS_ENABLE)
                .orderByAsc(PsyTest::getSort)
                .orderByDesc(PsyTest::getCreateTime)
                .page(page);
        // 查询题目数量
        result.getRecords().forEach(test -> {
            long count = questionMapper.selectCount(
                    new LambdaQueryWrapper<Question>().eq(Question::getTestId, test.getId()));
            test.setQuestionCount((int) count);
        });
        return result;
    }

    @Override
    public PsyTest getDetailWithQuestions(Long id) {
        PsyTest test = this.getById(id);
        if (test == null) {
            return null;
        }
        // 查询题目
        List<Question> questions = questionMapper.selectList(
                new LambdaQueryWrapper<Question>()
                        .eq(Question::getTestId, id)
                        .orderByAsc(Question::getSort));
        // 查询每道题的选项
        questions.forEach(question -> {
            List<Option> options = optionMapper.selectList(
                    new LambdaQueryWrapper<Option>()
                            .eq(Option::getQuestionId, question.getId())
                            .orderByAsc(Option::getSort));
            question.setOptions(options);
        });
        test.setQuestions(questions);
        test.setQuestionCount(questions.size());
        return test;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> submitTest(Long userId, Long testId, List<Map<String, Object>> answers) {
        PsyTest test = this.getById(testId);
        if (test == null) {
            throw new RuntimeException("测试卷不存在");
        }

        // 计算原始总分
        int rawScore = 0;
        for (Map<String, Object> answer : answers) {
            Object optionIdObj = answer.get("optionId");
            Long optionId = null;
            if (optionIdObj instanceof Number) {
                optionId = ((Number) optionIdObj).longValue();
            }
            if (optionId != null) {
                Option option = optionMapper.selectById(optionId);
                if (option != null) {
                    rawScore += option.getScore();
                }
            }
        }

        // 计算最大可能原始分（每道题的最高分之和），用于归一化
        List<Question> questions = questionMapper.selectList(
                new LambdaQueryWrapper<Question>().eq(Question::getTestId, testId));
        int maxRawScore = 0;
        for (Question question : questions) {
            List<Option> options = optionMapper.selectList(
                    new LambdaQueryWrapper<Option>().eq(Option::getQuestionId, question.getId()));
            int maxOptionScore = options.stream().mapToInt(Option::getScore).max().orElse(0);
            maxRawScore += maxOptionScore;
        }

        // 将原始分归一化到试卷满分
        int totalScore = rawScore;
        if (maxRawScore > 0) {
            totalScore = (int) Math.round((double) rawScore / maxRawScore * test.getTotalScore());
        }

        // 判断结果
        int result;
        String resultText;
        if (totalScore >= test.getGoodThreshold()) {
            result = Constants.HEALTH_STATUS_NEED_TALK;
            resultText = "建议沟通";
        } else if (totalScore >= test.getHealthThreshold()) {
            result = Constants.HEALTH_STATUS_GOOD;
            resultText = "良好";
        } else {
            result = Constants.HEALTH_STATUS_HEALTHY;
            resultText = "健康";
        }

        // 保存测试记录
        TestRecord record = new TestRecord();
        record.setUserId(userId);
        record.setTestId(testId);
        record.setTotalScore(totalScore);
        record.setResult(result);
        record.setAnswerDetail(JSONUtil.toJsonStr(answers));
        testRecordMapper.insert(record);

        // 更新用户健康状态
        userService.updateHealthStatus(userId, result);

        // 返回结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", record.getId());
        resultMap.put("totalScore", totalScore);
        resultMap.put("result", result);
        resultMap.put("resultText", resultText);
        resultMap.put("testTitle", test.getTitle());
        resultMap.put("healthThreshold", test.getHealthThreshold());
        resultMap.put("goodThreshold", test.getGoodThreshold());
        return resultMap;
    }
}
