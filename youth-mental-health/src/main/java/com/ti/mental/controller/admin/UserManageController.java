package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.User;
import com.ti.mental.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/admin/user")
public class UserManageController {

    @Resource
    private UserService userService;

    @Operation(summary = "分页查询用户")
    @GetMapping("/list")
    public Result<IPage<User>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer healthStatus) {
        IPage<User> page = userService.page(pageNum, pageSize, username, healthStatus);
        // 隐藏密码
        page.getRecords().forEach(user -> user.setPassword(null));
        return Result.success(page);
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public Result<User> detail(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @Operation(summary = "添加用户")
    @PostMapping
    public Result<Void> add(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @Operation(summary = "更新用户")
    @PutMapping
    public Result<Void> update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新用户状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userService.updateById(user);
        return Result.success();
    }

    @Operation(summary = "重置用户密码")
    @PutMapping("/resetPassword/{id}")
    public Result<Void> resetPassword(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        user.setPassword(com.ti.mental.common.utils.PasswordUtil.encrypt("123456"));
        userService.updateById(user);
        return Result.success();
    }
}
