package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.Category;
import com.ti.mental.mapper.CategoryMapper;
import com.ti.mental.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 心理分类Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public IPage<Category> page(Integer pageNum, Integer pageSize, String name) {
        Page<Category> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), Category::getName, name)
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<Category> listEnabled() {
        return this.lambdaQuery()
                .eq(Category::getStatus, Constants.STATUS_ENABLE)
                .orderByAsc(Category::getSort)
                .list();
    }
}
