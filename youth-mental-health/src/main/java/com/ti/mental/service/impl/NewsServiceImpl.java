package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.News;
import com.ti.mental.mapper.NewsMapper;
import com.ti.mental.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资讯Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public IPage<News> page(Integer pageNum, Integer pageSize, String title) {
        Page<News> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(title), News::getTitle, title)
                .orderByAsc(News::getSort)
                .orderByDesc(News::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public IPage<News> listEnabled(Integer pageNum, Integer pageSize) {
        Page<News> page = new Page<>(pageNum, pageSize);
        return this.lambdaQuery()
                .eq(News::getStatus, Constants.STATUS_ENABLE)
                .orderByAsc(News::getSort)
                .orderByDesc(News::getCreateTime)
                .page(page);
    }

    @Override
    public List<News> listHot(Integer limit) {
        return this.lambdaQuery()
                .eq(News::getStatus, Constants.STATUS_ENABLE)
                .eq(News::getIsHot, 1)
                .orderByDesc(News::getViewCount)
                .last("LIMIT " + limit)
                .list();
    }

    @Override
    public News getDetailAndIncrView(Long id) {
        News news = this.getById(id);
        if (news != null) {
            // 增加浏览量
            this.lambdaUpdate()
                    .eq(News::getId, id)
                    .setSql("view_count = view_count + 1")
                    .update();
            news.setViewCount(news.getViewCount() + 1);
        }
        return news;
    }
}
