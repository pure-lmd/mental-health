package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Category;

import java.util.List;

/**
 * 心理分类Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface CategoryService extends IService<Category> {

    /**
     * 分页查询分类
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param name     分类名称
     * @return 分页结果
     */
    IPage<Category> page(Integer pageNum, Integer pageSize, String name);

    /**
     * 获取所有启用的分类
     *
     * @return 分类列表
     */
    List<Category> listEnabled();
}
