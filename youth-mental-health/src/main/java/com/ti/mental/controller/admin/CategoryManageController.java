package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Category;
import com.ti.mental.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 心理分类管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "心理分类管理")
@RestController
@RequestMapping("/api/admin/category")
public class CategoryManageController {

    @Resource
    private CategoryService categoryService;

    @Operation(summary = "分页查询分类")
    @GetMapping("/list")
    public Result<IPage<Category>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        return Result.success(categoryService.page(pageNum, pageSize, name));
    }

    @Operation(summary = "获取所有启用分类")
    @GetMapping("/all")
    public Result<List<Category>> all() {
        return Result.success(categoryService.listEnabled());
    }

    @Operation(summary = "获取分类详情")
    @GetMapping("/{id}")
    public Result<Category> detail(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @Operation(summary = "添加分类")
    @PostMapping
    public Result<Void> add(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    @Operation(summary = "更新分类")
    @PutMapping
    public Result<Void> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新分类状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        categoryService.updateById(category);
        return Result.success();
    }
}
