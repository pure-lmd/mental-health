package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Comment;
import com.ti.mental.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 评论管理 Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "评论管理")
@RestController
@RequestMapping("/api/admin/comment")
public class CommentManageController {

    @Resource
    private CommentService commentService;

    @Operation(summary = "分页查询评论")
    @GetMapping("/list")
    public Result<IPage<Comment>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {
        return Result.success(commentService.page(pageNum, pageSize, type, status));
    }

    @Operation(summary = "获取评论详情")
    @GetMapping("/{id}")
    public Result<Comment> detail(@PathVariable Long id) {
        return Result.success(commentService.getById(id));
    }

    @Operation(summary = "更新评论状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        commentService.updateStatus(id, status);
        return Result.success();
    }

    @Operation(summary = "审核评论")
    @PutMapping("/audit")
    public Result<Void> audit(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Integer status = (Integer) params.get("status");
        String auditRemark = params.get("auditRemark") != null ? params.get("auditRemark").toString() : "";
        commentService.audit(id, status, auditRemark);
        return Result.success();
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        commentService.removeById(id);
        return Result.success();
    }
}
