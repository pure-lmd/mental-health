package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Admin;
import com.ti.mental.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员Controller
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "管理员管理")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String token = adminService.login(username, password);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        return Result.success(result);
    }

    @Operation(summary = "获取当前管理员信息")
    @GetMapping("/info")
    public Result<Admin> info(HttpServletRequest request) {
        Long adminId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        Admin admin = adminService.getById(adminId);
        admin.setPassword(null);
        return Result.success(admin);
    }

    @Operation(summary = "修改当前管理员信息")
    @PutMapping("/info")
    public Result<Void> updateInfo(HttpServletRequest request, @RequestBody Admin admin) {
        Long adminId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        admin.setId(adminId);
        adminService.update(admin);
        return Result.success();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public Result<Void> updatePassword(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long adminId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        adminService.updatePassword(adminId, oldPassword, newPassword);
        return Result.success();
    }

    @Operation(summary = "分页查询管理员")
    @GetMapping("/list")
    public Result<IPage<Admin>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username) {
        IPage<Admin> page = adminService.page(pageNum, pageSize, username);
        // 隐藏密码
        page.getRecords().forEach(admin -> admin.setPassword(null));
        return Result.success(page);
    }

    @Operation(summary = "添加管理员")
    @PostMapping
    public Result<Void> add(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @Operation(summary = "更新管理员")
    @PutMapping
    public Result<Void> update(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }

    @Operation(summary = "删除管理员")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新管理员状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Admin admin = new Admin();
        admin.setId(id);
        admin.setStatus(status);
        adminService.updateById(admin);
        return Result.success();
    }
}
