package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.PsyTest;
import com.ti.mental.entity.TestRecord;
import com.ti.mental.service.PsyTestService;
import com.ti.mental.service.TestRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 心理测试Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "心理测试")
@RestController
@RequestMapping("/api/front/test")
public class TestFrontController {

    @Resource
    private PsyTestService psyTestService;

    @Resource
    private TestRecordService testRecordService;

    @Operation(summary = "获取测试列表")
    @GetMapping("/list")
    public Result<IPage<PsyTest>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(psyTestService.listEnabled(pageNum, pageSize));
    }

    @Operation(summary = "获取测试详情（含题目）")
    @GetMapping("/{id}")
    public Result<PsyTest> detail(@PathVariable Long id) {
        return Result.success(psyTestService.getDetailWithQuestions(id));
    }

    @Operation(summary = "提交测试")
    @PostMapping("/submit")
    public Result<Map<String, Object>> submit(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        Long testId = Long.valueOf(params.get("testId").toString());
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> answers = (List<Map<String, Object>>) params.get("answers");
        Map<String, Object> result = psyTestService.submitTest(userId, testId, answers);
        return Result.success(result);
    }

    @Operation(summary = "获取我的测试记录")
    @GetMapping("/record")
    public Result<IPage<TestRecord>> record(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        return Result.success(testRecordService.listByUserId(userId, pageNum, pageSize));
    }

    @Operation(summary = "获取测试记录详情")
    @GetMapping("/record/{id}")
    public Result<TestRecord> recordDetail(@PathVariable Long id) {
        TestRecord record = testRecordService.getById(id);
        if (record != null && record.getTestId() != null) {
            PsyTest test = psyTestService.getById(record.getTestId());
            if (test != null) {
                record.setTestTitle(test.getTitle());
            }
        }
        return Result.success(record);
    }
}
