package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Comment;
import com.ti.mental.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 评论Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "评论")
@RestController
@RequestMapping("/api/front/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Operation(summary = "发表评论")
    @PostMapping
    public Result<Void> add(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        Long targetId = Long.valueOf(params.get("targetId").toString());
        Integer type = Integer.valueOf(params.get("type").toString());
        String content = params.get("content").toString();
        Long parentId = params.get("parentId") != null ? Long.valueOf(params.get("parentId").toString()) : null;
        commentService.add(userId, targetId, type, content, parentId);
        return Result.success();
    }

    @Operation(summary = "获取评论列表")
    @GetMapping("/list")
    public Result<IPage<Comment>> list(
            @RequestParam Long targetId,
            @RequestParam Integer type,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.listByTarget(targetId, type, pageNum, pageSize));
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{id}")
    public Result<Void> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        Comment comment = commentService.getById(id);
        if (comment != null && comment.getUserId().equals(userId)) {
            commentService.removeById(id);
        }
        return Result.success();
    }
}
