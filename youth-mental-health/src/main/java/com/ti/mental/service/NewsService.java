package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.News;

import java.util.List;

/**
 * 资讯Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface NewsService extends IService<News> {

    /**
     * 分页查询资讯（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param title    标题
     * @return 分页结果
     */
    IPage<News> page(Integer pageNum, Integer pageSize, String title);

    /**
     * 分页查询资讯（用户端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<News> listEnabled(Integer pageNum, Integer pageSize);

    /**
     * 获取热门资讯
     *
     * @param limit 数量限制
     * @return 资讯列表
     */
    List<News> listHot(Integer limit);

    /**
     * 获取详情并增加浏览量
     *
     * @param id 资讯ID
     * @return 资讯详情
     */
    News getDetailAndIncrView(Long id);
}
