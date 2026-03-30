package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Publicity;
import com.ti.mental.service.PublicityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 心理宣传Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "心理宣传")
@RestController
@RequestMapping("/api/front/publicity")
public class PublicityFrontController {

    @Resource
    private PublicityService publicityService;

    @Operation(summary = "获取宣传列表")
    @GetMapping("/list")
    public Result<IPage<Publicity>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(publicityService.listEnabled(pageNum, pageSize));
    }

    @Operation(summary = "获取宣传详情")
    @GetMapping("/{id}")
    public Result<Publicity> detail(@PathVariable Long id) {
        return Result.success(publicityService.getDetailAndIncrView(id));
    }
}
