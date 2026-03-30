package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.Knowledge;
import com.ti.mental.mapper.KnowledgeMapper;
import com.ti.mental.service.KnowledgeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 心理科普Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Override
    public IPage<Knowledge> page(Integer pageNum, Integer pageSize, Long categoryId, String title) {
        Page<Knowledge> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithCategory(page, categoryId, title, null);
    }

    @Override
    public IPage<Knowledge> listEnabled(Integer pageNum, Integer pageSize, Long categoryId, String keyword) {
        Page<Knowledge> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithCategory(page, categoryId, keyword, Constants.STATUS_ENABLE);
    }

    @Override
    public List<Knowledge> listHot(Integer limit) {
        return this.lambdaQuery()
                .eq(Knowledge::getStatus, Constants.STATUS_ENABLE)
                .eq(Knowledge::getIsHot, 1)
                .orderByDesc(Knowledge::getViewCount)
                .last("LIMIT " + limit)
                .list();
    }

    @Override
    public Knowledge getDetailAndIncrView(Long id) {
        Knowledge knowledge = this.getById(id);
        if (knowledge != null) {
            // 增加浏览量
            this.lambdaUpdate()
                    .eq(Knowledge::getId, id)
                    .setSql("view_count = view_count + 1")
                    .update();
            knowledge.setViewCount(knowledge.getViewCount() + 1);
        }
        return knowledge;
    }

    @Override
    public void incrCollectCount(Long id, int delta) {
        this.lambdaUpdate()
                .eq(Knowledge::getId, id)
                .setSql("collect_count = collect_count + " + delta)
                .update();
    }
}
