package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Registration;
import com.ti.mental.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 报名管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "报名管理")
@RestController
@RequestMapping("/api/admin/registration")
public class RegistrationManageController {

    @Resource
    private RegistrationService registrationService;

    @Operation(summary = "分页查询报名记录")
    @GetMapping("/list")
    public Result<IPage<Registration>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long activityId,
            @RequestParam(required = false) Integer status) {
        return Result.success(registrationService.page(pageNum, pageSize, activityId, status));
    }

    @Operation(summary = "获取报名详情")
    @GetMapping("/{id}")
    public Result<Registration> detail(@PathVariable Long id) {
        return Result.success(registrationService.getById(id));
    }

    @Operation(summary = "审核报名")
    @PutMapping("/audit")
    public Result<Void> audit(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Integer status = Integer.valueOf(params.get("status").toString());
        String auditRemark = params.get("auditRemark") != null ? params.get("auditRemark").toString() : "";
        registrationService.audit(id, status, auditRemark);
        return Result.success();
    }

    @Operation(summary = "删除报名记录")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        registrationService.removeById(id);
        return Result.success();
    }
}
