package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.exception.BusinessException;
import com.ti.mental.common.result.Result;
import com.ti.mental.common.result.ResultCode;
import com.ti.mental.common.utils.JwtUtil;
import com.ti.mental.entity.Activity;
import com.ti.mental.entity.Registration;
import com.ti.mental.service.ActivityService;
import com.ti.mental.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "活动")
@RestController
@RequestMapping("/api/front/activity")
public class ActivityFrontController {

    @Resource
    private ActivityService activityService;

    @Resource
    private RegistrationService registrationService;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 从请求中手动解析token获取userId（用于被排除在拦截器之外的接口）
     */
    private Long getUserIdFromRequest(HttpServletRequest request) {
        // 优先从拦截器设置的attribute中获取
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        if (userId != null) {
            return userId;
        }
        // 手动解析token
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (StrUtil.isNotBlank(token)) {
            return jwtUtil.getUserIdFromToken(token);
        }
        return null;
    }

    @Operation(summary = "获取活动列表")
    @GetMapping("/list")
    public Result<IPage<Activity>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(activityService.listEnabled(pageNum, pageSize));
    }

    @Operation(summary = "获取活动详情")
    @GetMapping("/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id, HttpServletRequest request) {
        Activity activity = activityService.getById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("activity", activity);
        
        // 检查是否已报名（如果已登录）
        Long userId = getUserIdFromRequest(request);
        if (userId != null) {
            result.put("hasRegistered", registrationService.hasRegistered(userId, id));
        } else {
            result.put("hasRegistered", false);
        }
        
        return Result.success(result);
    }

    @Operation(summary = "活动报名")
    @PostMapping("/register")
    public Result<Void> register(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = getUserIdFromRequest(request);
        if (userId == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        Long activityId = Long.valueOf(params.get("activityId").toString());
        String remark = params.get("remark") != null ? params.get("remark").toString() : "";
        registrationService.register(userId, activityId, remark);
        return Result.success();
    }

    @Operation(summary = "取消报名")
    @DeleteMapping("/registration/{id}")
    public Result<Void> cancelRegistration(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        registrationService.cancel(id, userId);
        return Result.success();
    }

    @Operation(summary = "获取我的报名列表")
    @GetMapping("/registration/my")
    public Result<IPage<Registration>> myRegistrations(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        return Result.success(registrationService.listByUserId(userId, pageNum, pageSize));
    }
}
