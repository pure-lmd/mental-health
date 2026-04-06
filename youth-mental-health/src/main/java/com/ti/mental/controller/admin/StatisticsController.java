package com.ti.mental.controller.admin;

import com.ti.mental.common.result.Result;
import com.ti.mental.service.StatisticsService;
import com.ti.mental.service.WarningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @Resource
    private WarningService warningService;

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

    @Operation(summary = "获取健康趋势分析")
    @GetMapping("/health-trend")
    public Result<Map<String, Object>> healthTrend() {
        return Result.success(statisticsService.getHealthTrend());
    }

    @Operation(summary = "获取高风险用户预警列表")
    @GetMapping("/warning-list")
    public Result<List<Map<String, Object>>> warningList() {
        return Result.success(warningService.getWarningUsers());
    }

    @Operation(summary = "获取活动效果评估")
    @GetMapping("/activity-effect")
    public Result<Map<String, Object>> activityEffect() {
        return Result.success(statisticsService.getActivityEffect());
    }

    @Operation(summary = "获取科普效果评估")
    @GetMapping("/knowledge-effect")
    public Result<Map<String, Object>> knowledgeEffect() {
        return Result.success(statisticsService.getKnowledgeEffect());
    }
}
