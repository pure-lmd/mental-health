package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Feedback;
import com.ti.mental.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 反馈Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "意见反馈")
@RestController
@RequestMapping("/api/front/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @Operation(summary = "提交反馈")
    @PostMapping
    public Result<Void> submit(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        String title = params.get("title").toString();
        String content = params.get("content").toString();
        String images = params.get("images") != null ? params.get("images").toString() : null;
        feedbackService.submit(userId, title, content, images);
        return Result.success();
    }

    @Operation(summary = "获取我的反馈列表")
    @GetMapping("/my")
    public Result<IPage<Feedback>> my(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        return Result.success(feedbackService.listByUserId(userId, pageNum, pageSize));
    }

    @Operation(summary = "获取反馈详情")
    @GetMapping("/{id}")
    public Result<Feedback> detail(@PathVariable Long id) {
        return Result.success(feedbackService.getById(id));
    }

    @Operation(summary = "更新反馈")
    @PutMapping("/{id}")
    public Result<Void> update(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        String title = params.get("title").toString();
        String content = params.get("content").toString();
        String images = params.get("images") != null ? params.get("images").toString() : null;
        feedbackService.updateByUser(id, userId, title, content, images);
        return Result.success();
    }

    @Operation(summary = "删除反馈")
    @DeleteMapping("/{id}")
    public Result<Void> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        feedbackService.deleteByUser(id, userId);
        return Result.success();
    }
}
