package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Feedback;
import com.ti.mental.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 反馈管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "反馈管理")
@RestController
@RequestMapping("/api/admin/feedback")
public class FeedbackManageController {

    @Resource
    private FeedbackService feedbackService;

    @Operation(summary = "分页查询反馈")
    @GetMapping("/list")
    public Result<IPage<Feedback>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        return Result.success(feedbackService.page(pageNum, pageSize, status));
    }

    @Operation(summary = "获取反馈详情")
    @GetMapping("/{id}")
    public Result<Feedback> detail(@PathVariable Long id) {
        return Result.success(feedbackService.getById(id));
    }

    @Operation(summary = "回复反馈")
    @PutMapping("/reply")
    public Result<Void> reply(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String reply = params.get("reply").toString();
        feedbackService.reply(id, reply);
        return Result.success();
    }

    @Operation(summary = "删除反馈")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        feedbackService.removeById(id);
        return Result.success();
    }
}
