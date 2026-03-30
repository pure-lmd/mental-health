package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Notice;
import com.ti.mental.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "通知公告")
@RestController
@RequestMapping("/api/front/notice")
public class NoticeFrontController {

    @Resource
    private NoticeService noticeService;

    @Operation(summary = "获取通知公告列表")
    @GetMapping("/list")
    public Result<IPage<Notice>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(noticeService.listEnabled(pageNum, pageSize));
    }

    @Operation(summary = "获取通知公告详情")
    @GetMapping("/{id}")
    public Result<Notice> detail(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }
}
