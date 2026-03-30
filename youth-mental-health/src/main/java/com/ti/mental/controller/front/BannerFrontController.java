package com.ti.mental.controller.front;

import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Banner;
import com.ti.mental.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 轮播图Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "轮播图")
@RestController
@RequestMapping("/api/front/banner")
public class BannerFrontController {

    @Resource
    private BannerService bannerService;

    @Operation(summary = "获取轮播图列表")
    @GetMapping("/list")
    public Result<List<Banner>> list() {
        return Result.success(bannerService.listEnabled());
    }
}
