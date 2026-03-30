package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.News;
import com.ti.mental.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资讯Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "资讯")
@RestController
@RequestMapping("/api/front/news")
public class NewsFrontController {

    @Resource
    private NewsService newsService;

    @Operation(summary = "获取资讯列表")
    @GetMapping("/list")
    public Result<IPage<News>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(newsService.listEnabled(pageNum, pageSize));
    }

    @Operation(summary = "获取热门资讯")
    @GetMapping("/hot")
    public Result<List<News>> hot(@RequestParam(defaultValue = "5") Integer limit) {
        return Result.success(newsService.listHot(limit));
    }

    @Operation(summary = "获取资讯详情")
    @GetMapping("/{id}")
    public Result<News> detail(@PathVariable Long id) {
        return Result.success(newsService.getDetailAndIncrView(id));
    }
}
