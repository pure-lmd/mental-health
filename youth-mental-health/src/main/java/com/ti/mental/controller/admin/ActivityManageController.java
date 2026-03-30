package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Activity;
import com.ti.mental.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 活动管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "活动管理")
@RestController
@RequestMapping("/api/admin/activity")
public class ActivityManageController {

    @Resource
    private ActivityService activityService;

    @Operation(summary = "分页查询活动")
    @GetMapping("/list")
    public Result<IPage<Activity>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer status) {
        return Result.success(activityService.page(pageNum, pageSize, title, status));
    }

    @Operation(summary = "获取活动详情")
    @GetMapping("/{id}")
    public Result<Activity> detail(@PathVariable Long id) {
        return Result.success(activityService.getById(id));
    }

    @Operation(summary = "添加活动")
    @PostMapping
    public Result<Void> add(@RequestBody Activity activity) {
        activityService.save(activity);
        return Result.success();
    }

    @Operation(summary = "更新活动")
    @PutMapping
    public Result<Void> update(@RequestBody Activity activity) {
        activityService.updateById(activity);
        return Result.success();
    }

    @Operation(summary = "删除活动")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        activityService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新活动状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(status);
        activityService.updateById(activity);
        return Result.success();
    }

    @Operation(summary = "设置热门")
    @PutMapping("/hot/{id}")
    public Result<Void> setHot(@PathVariable Long id, @RequestParam Integer isHot) {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setIsHot(isHot);
        activityService.updateById(activity);
        return Result.success();
    }
}
