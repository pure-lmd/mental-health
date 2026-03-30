package com.ti.mental.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.Knowledge;
import com.ti.mental.service.KnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 心理科普管理Controller（管理端）
 *
 * @author Ti
 * @date 2026-02-03
 */
@Tag(name = "心理科普管理")
@RestController
@RequestMapping("/api/admin/knowledge")
public class KnowledgeManageController {

    @Resource
    private KnowledgeService knowledgeService;

    @Operation(summary = "分页查询科普")
    @GetMapping("/list")
    public Result<IPage<Knowledge>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String title) {
        return Result.success(knowledgeService.page(pageNum, pageSize, categoryId, title));
    }

    @Operation(summary = "获取科普详情")
    @GetMapping("/{id}")
    public Result<Knowledge> detail(@PathVariable Long id) {
        return Result.success(knowledgeService.getById(id));
    }

    @Operation(summary = "添加科普")
    @PostMapping
    public Result<Void> add(@RequestBody Knowledge knowledge) {
        knowledgeService.save(knowledge);
        return Result.success();
    }

    @Operation(summary = "更新科普")
    @PutMapping
    public Result<Void> update(@RequestBody Knowledge knowledge) {
        knowledgeService.updateById(knowledge);
        return Result.success();
    }

    @Operation(summary = "删除科普")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        knowledgeService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "更新科普状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Knowledge knowledge = new Knowledge();
        knowledge.setId(id);
        knowledge.setStatus(status);
        knowledgeService.updateById(knowledge);
        return Result.success();
    }

    @Operation(summary = "设置热门")
    @PutMapping("/hot/{id}")
    public Result<Void> setHot(@PathVariable Long id, @RequestParam Integer isHot) {
        Knowledge knowledge = new Knowledge();
        knowledge.setId(id);
        knowledge.setIsHot(isHot);
        knowledgeService.updateById(knowledge);
        return Result.success();
    }
}
