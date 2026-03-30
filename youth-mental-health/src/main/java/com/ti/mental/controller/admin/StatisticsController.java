package com.ti.mental.controller.admin;

import com.ti.mental.common.result.Result;
import com.ti.mental.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据统计Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "数据统计")
@RestController
@RequestMapping("/api/admin/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @Operation(summary = "获取概览数据")
    @GetMapping("/overview")
    public Result<Map<String, Object>> overview() {
        return Result.success(statisticsService.getOverview());
    }

    @Operation(summary = "获取测试统计")
    @GetMapping("/test")
    public Result<Map<String, Object>> test() {
        return Result.success(statisticsService.getTestStatistics());
    }

    @Operation(summary = "获取健康状态统计")
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        return Result.success(statisticsService.getHealthStatistics());
    }

    @Operation(summary = "获取活动统计")
    @GetMapping("/activity")
    public Result<Map<String, Object>> activity() {
        return Result.success(statisticsService.getActivityStatistics());
    }
}
