package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Category;
import com.ti.mental.entity.Comment;
import com.ti.mental.entity.Knowledge;
import com.ti.mental.common.utils.JwtUtil;
import com.ti.mental.service.CategoryService;
import com.ti.mental.service.CollectService;
import com.ti.mental.service.CommentService;
import com.ti.mental.service.KnowledgeService;
import com.ti.mental.service.LearningRecordService;
import com.ti.mental.service.PointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 心理科普Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "心理科普")
@RestController
@RequestMapping("/api/front/knowledge")
public class KnowledgeFrontController {

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private CollectService collectService;

    @Resource
    private CommentService commentService;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private LearningRecordService learningRecordService;

    @Resource
    private PointsService pointsService;

    @Operation(summary = "获取分类列表")
    @GetMapping("/category/list")
    public Result<List<Category>> categoryList() {
        return Result.success(categoryService.listEnabled());
    }

    @Operation(summary = "分页查询科普列表")
    @GetMapping("/list")
    public Result<IPage<Knowledge>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword) {
        return Result.success(knowledgeService.listEnabled(pageNum, pageSize, categoryId, keyword));
    }

    @Operation(summary = "获取科普详情")
    @GetMapping("/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id, HttpServletRequest request) {
        Knowledge knowledge = knowledgeService.getDetailAndIncrView(id);
        Map<String, Object> result = new HashMap<>();
        result.put("knowledge", knowledge);

        // 检查是否已收藏（如果已登录）
        // 该接口被排除在拦截器之外，需手动解析token获取userId
        Long userId = null;
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (StrUtil.isNotBlank(token)) {
            userId = jwtUtil.getUserIdFromToken(token);
        }
        if (userId != null) {
            result.put("isCollected", collectService.hasCollected(userId, id, Constants.COLLECT_TYPE_KNOWLEDGE));
            
            // 新增: 记录学习行为(浏览)
            learningRecordService.record(userId, 1, id, 1, 0);
            
            // 新增: 奖励积分(每次浏览都给)
            pointsService.rewardForLearning(userId, id);
        } else {
            result.put("isCollected", false);
        }

        return Result.success(result);
    }

    @Operation(summary = "获取科普评论列表")
    @GetMapping("/{id}/comments")
    public Result<IPage<Comment>> comments(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.listByTarget(id, Constants.COLLECT_TYPE_KNOWLEDGE, pageNum, pageSize));
    }
}
