package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.PsyTest;
import com.ti.mental.entity.Question;
import com.ti.mental.service.PsyTestService;
import com.ti.mental.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 心理测试管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "心理测试管理")
@RestController
@RequestMapping("/api/admin/test")
public class TestManageController {

    @Resource
    private PsyTestService psyTestService;

    @Resource
    private QuestionService questionService;

    @Operation(summary = "分页查询测试卷")
    @GetMapping("/list")
    public Result<IPage<PsyTest>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        return Result.success(psyTestService.page(pageNum, pageSize, title));
    }

    @Operation(summary = "获取测试卷详情")
    @GetMapping("/{id}")
    public Result<PsyTest> detail(@PathVariable Long id) {
        return Result.success(psyTestService.getDetailWithQuestions(id));
    }

    @Operation(summary = "添加测试卷")
    @PostMapping
    public Result<Void> add(@RequestBody PsyTest psyTest) {
        psyTestService.save(psyTest);
        return Result.success();
    }

    @Operation(summary = "更新测试卷")
    @PutMapping
    public Result<Void> update(@RequestBody PsyTest psyTest) {
        psyTestService.updateById(psyTest);
        return Result.success();
    }

    @Operation(summary = "删除测试卷")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        psyTestService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新测试卷状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        PsyTest psyTest = new PsyTest();
        psyTest.setId(id);
        psyTest.setStatus(status);
        psyTestService.updateById(psyTest);
        return Result.success();
    }

    // ========== 题目管理 ==========

    @Operation(summary = "获取测试卷的所有题目")
    @GetMapping("/{testId}/questions")
    public Result<List<Question>> listQuestions(@PathVariable Long testId) {
        return Result.success(questionService.listByTestId(testId));
    }

    @Operation(summary = "添加题目（含选项）")
    @PostMapping("/question")
    public Result<Void> addQuestion(@RequestBody Question question) {
        questionService.addWithOptions(question);
        return Result.success();
    }

    @Operation(summary = "更新题目（含选项）")
    @PutMapping("/question")
    public Result<Void> updateQuestion(@RequestBody Question question) {
        questionService.updateWithOptions(question);
        return Result.success();
    }

    @Operation(summary = "删除题目（含选项）")
    @DeleteMapping("/question/{id}")
    public Result<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteWithOptions(id);
        return Result.success();
    }
}
