package com.ti.mental.controller.front;

import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.User;
import com.ti.mental.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "用户中心")
@RestController
@RequestMapping("/api/front/user")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<User> info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        User user = userService.getById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @Operation(summary = "修改当前用户信息")
    @PutMapping("/info")
    public Result<Void> updateInfo(HttpServletRequest request, @RequestBody User user) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        user.setId(userId);
        userService.update(user);
        return Result.success();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public Result<Void> updatePassword(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        userService.updatePassword(userId, oldPassword, newPassword);
        return Result.success();
    }
}
