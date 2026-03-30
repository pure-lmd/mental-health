package com.ti.mental.controller.front;

import com.ti.mental.common.result.Result;
import com.ti.mental.entity.*;
import com.ti.mental.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "首页")
@RestController
@RequestMapping("/api/front/home")
public class HomeController {

    @Resource
    private BannerService bannerService;

    @Resource
    private NoticeService noticeService;

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private ActivityService activityService;

    @Resource
    private CategoryService categoryService;

    @Operation(summary = "获取首页数据")
    @GetMapping("/data")
    public Result<Map<String, Object>> homeData() {
        Map<String, Object> result = new HashMap<>();
        // 轮播图
        result.put("banners", bannerService.listEnabled());
        // 最新公告
        result.put("notices", noticeService.listLatest(5));
        // 热门科普
        result.put("hotKnowledge", knowledgeService.listHot(6));
        // 热门活动
        result.put("hotActivities", activityService.listHot(4));
        // 分类列表
        result.put("categories", categoryService.listEnabled());
        return Result.success(result);
    }

    @Operation(summary = "获取轮播图列表")
    @GetMapping("/banner/list")
    public Result<List<Banner>> bannerList() {
        return Result.success(bannerService.listEnabled());
    }

    @Operation(summary = "获取最新公告")
    @GetMapping("/notice/latest")
    public Result<List<Notice>> noticeLatest(@RequestParam(defaultValue = "5") Integer limit) {
        return Result.success(noticeService.listLatest(limit));
    }

    @Operation(summary = "获取热门科普")
    @GetMapping("/knowledge/hot")
    public Result<List<Knowledge>> knowledgeHot(@RequestParam(defaultValue = "6") Integer limit) {
        return Result.success(knowledgeService.listHot(limit));
    }

    @Operation(summary = "获取热门活动")
    @GetMapping("/activity/hot")
    public Result<List<Activity>> activityHot(@RequestParam(defaultValue = "4") Integer limit) {
        return Result.success(activityService.listHot(limit));
    }
}
