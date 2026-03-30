package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.TestRecord;
import com.ti.mental.service.TestRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 测试记录管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "测试记录管理")
@RestController
@RequestMapping("/api/admin/testRecord")
public class TestRecordManageController {

    @Resource
    private TestRecordService testRecordService;

    @Operation(summary = "分页查询测试记录")
    @GetMapping("/list")
    public Result<IPage<TestRecord>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long testId) {
        return Result.success(testRecordService.page(pageNum, pageSize, userId, testId));
    }

    @Operation(summary = "获取测试记录详情")
    @GetMapping("/{id}")
    public Result<TestRecord> detail(@PathVariable Long id) {
        return Result.success(testRecordService.getById(id));
    }

    @Operation(summary = "删除测试记录")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        testRecordService.removeById(id);
        return Result.success();
    }
}
