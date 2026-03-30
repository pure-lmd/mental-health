package com.ti.mental.controller.front;

import com.ti.mental.common.result.Result;
import com.ti.mental.entity.User;
import com.ti.mental.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户认证Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "用户认证")
@RestController
@RequestMapping("/api/front")
public class AuthController {

    @Resource
    private UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String token = userService.login(username, password);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        return Result.success(result);
    }
}
