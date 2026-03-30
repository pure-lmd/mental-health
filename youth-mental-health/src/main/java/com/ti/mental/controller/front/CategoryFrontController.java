package com.ti.mental.controller.front;

import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Category;
import com.ti.mental.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类Controller（用户端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "分类")
@RestController
@RequestMapping("/api/front/category")
public class CategoryFrontController {

    @Resource
    private CategoryService categoryService;

    @Operation(summary = "获取分类列表")
    @GetMapping("/list")
    public Result<List<Category>> list() {
        return Result.success(categoryService.listEnabled());
    }
}
