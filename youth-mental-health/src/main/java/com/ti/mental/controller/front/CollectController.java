package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Collect;
import com.ti.mental.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 收藏Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "收藏")
@RestController
@RequestMapping("/api/front/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    @Operation(summary = "添加收藏")
    @PostMapping
    public Result<Void> add(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        Long targetId = Long.valueOf(params.get("targetId").toString());
        Integer type = Integer.valueOf(params.get("type").toString());
        collectService.add(userId, targetId, type);
        return Result.success();
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping
    public Result<Void> remove(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        Long targetId = Long.valueOf(params.get("targetId").toString());
        Integer type = Integer.valueOf(params.get("type").toString());
        collectService.remove(userId, targetId, type);
        return Result.success();
    }

    @Operation(summary = "检查是否已收藏")
    @GetMapping("/check")
    public Result<Boolean> check(
            HttpServletRequest request,
            @RequestParam Long targetId,
            @RequestParam Integer type) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        return Result.success(collectService.hasCollected(userId, targetId, type));
    }

    @Operation(summary = "获取我的收藏列表")
    @GetMapping("/my")
    public Result<IPage<Collect>> my(
            HttpServletRequest request,
            @RequestParam(required = false) Integer type,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        return Result.success(collectService.listByUserId(userId, type, pageNum, pageSize));
    }
}
