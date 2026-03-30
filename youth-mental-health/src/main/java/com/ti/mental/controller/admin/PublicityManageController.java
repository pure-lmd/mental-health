package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Publicity;
import com.ti.mental.service.PublicityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 心理宣传管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "心理宣传管理")
@RestController
@RequestMapping("/api/admin/publicity")
public class PublicityManageController {

    @Resource
    private PublicityService publicityService;

    @Operation(summary = "分页查询宣传")
    @GetMapping("/list")
    public Result<IPage<Publicity>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        return Result.success(publicityService.page(pageNum, pageSize, title));
    }

    @Operation(summary = "获取宣传详情")
    @GetMapping("/{id}")
    public Result<Publicity> detail(@PathVariable Long id) {
        return Result.success(publicityService.getById(id));
    }

    @Operation(summary = "添加宣传")
    @PostMapping
    public Result<Void> add(@RequestBody Publicity publicity) {
        publicityService.save(publicity);
        return Result.success();
    }

    @Operation(summary = "更新宣传")
    @PutMapping
    public Result<Void> update(@RequestBody Publicity publicity) {
        publicityService.updateById(publicity);
        return Result.success();
    }

    @Operation(summary = "删除宣传")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        publicityService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新宣传状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Publicity publicity = new Publicity();
        publicity.setId(id);
        publicity.setStatus(status);
        publicityService.updateById(publicity);
        return Result.success();
    }
}
