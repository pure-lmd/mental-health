package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Banner;
import com.ti.mental.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "轮播图管理")
@RestController
@RequestMapping("/api/admin/banner")
public class BannerManageController {

    @Resource
    private BannerService bannerService;

    @Operation(summary = "分页查询轮播图")
    @GetMapping("/list")
    public Result<IPage<Banner>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(bannerService.page(pageNum, pageSize));
    }

    @Operation(summary = "获取轮播图详情")
    @GetMapping("/{id}")
    public Result<Banner> detail(@PathVariable Long id) {
        return Result.success(bannerService.getById(id));
    }

    @Operation(summary = "添加轮播图")
    @PostMapping
    public Result<Void> add(@RequestBody Banner banner) {
        bannerService.save(banner);
        return Result.success();
    }

    @Operation(summary = "更新轮播图")
    @PutMapping
    public Result<Void> update(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return Result.success();
    }

    @Operation(summary = "删除轮播图")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bannerService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新轮播图状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Banner banner = new Banner();
        banner.setId(id);
        banner.setStatus(status);
        bannerService.updateById(banner);
        return Result.success();
    }
}
