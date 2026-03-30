package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Notice;
import com.ti.mental.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "通知公告管理")
@RestController
@RequestMapping("/api/admin/notice")
public class NoticeManageController {

    @Resource
    private NoticeService noticeService;

    @Operation(summary = "分页查询通知公告")
    @GetMapping("/list")
    public Result<IPage<Notice>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer type) {
        return Result.success(noticeService.page(pageNum, pageSize, title, type));
    }

    @Operation(summary = "获取通知公告详情")
    @GetMapping("/{id}")
    public Result<Notice> detail(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @Operation(summary = "添加通知公告")
    @PostMapping
    public Result<Void> add(@RequestBody Notice notice) {
        noticeService.save(notice);
        return Result.success();
    }

    @Operation(summary = "更新通知公告")
    @PutMapping
    public Result<Void> update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    @Operation(summary = "删除通知公告")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noticeService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新通知公告状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setStatus(status);
        noticeService.updateById(notice);
        return Result.success();
    }

    @Operation(summary = "设置置顶")
    @PutMapping("/top/{id}")
    public Result<Void> setTop(@PathVariable Long id, @RequestParam Integer isTop) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setIsTop(isTop);
        noticeService.updateById(notice);
        return Result.success();
    }
}
