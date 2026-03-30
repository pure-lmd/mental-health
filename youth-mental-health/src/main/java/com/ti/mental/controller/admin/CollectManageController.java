package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Collect;
import com.ti.mental.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 收藏管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "收藏管理")
@RestController
@RequestMapping("/api/admin/collect")
public class CollectManageController {

    @Resource
    private CollectService collectService;

    @Operation(summary = "分页查询收藏记录")
    @GetMapping("/list")
    public Result<IPage<Collect>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer type) {
        return Result.success(collectService.page(pageNum, pageSize, userId, type));
    }

    @Operation(summary = "删除收藏记录")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        collectService.removeById(id);
        return Result.success();
    }
}
