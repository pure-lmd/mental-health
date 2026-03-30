package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.News;
import com.ti.mental.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 资讯管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "资讯管理")
@RestController
@RequestMapping("/api/admin/news")
public class NewsManageController {

    @Resource
    private NewsService newsService;

    @Operation(summary = "分页查询资讯")
    @GetMapping("/list")
    public Result<IPage<News>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {
        return Result.success(newsService.page(pageNum, pageSize, title));
    }

    @Operation(summary = "获取资讯详情")
    @GetMapping("/{id}")
    public Result<News> detail(@PathVariable Long id) {
        return Result.success(newsService.getById(id));
    }

    @Operation(summary = "添加资讯")
    @PostMapping
    public Result<Void> add(@RequestBody News news) {
        newsService.save(news);
        return Result.success();
    }

    @Operation(summary = "更新资讯")
    @PutMapping
    public Result<Void> update(@RequestBody News news) {
        newsService.updateById(news);
        return Result.success();
    }

    @Operation(summary = "删除资讯")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        newsService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新资讯状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        News news = new News();
        news.setId(id);
        news.setStatus(status);
        newsService.updateById(news);
        return Result.success();
    }

    @Operation(summary = "设置热门")
    @PutMapping("/hot/{id}")
    public Result<Void> setHot(@PathVariable Long id, @RequestParam Integer isHot) {
        News news = new News();
        news.setId(id);
        news.setIsHot(isHot);
        newsService.updateById(news);
        return Result.success();
    }
}
