package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Knowledge;

import java.util.List;

/**
 * 心理科普Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface KnowledgeService extends IService<Knowledge> {

    /**
     * 分页查询科普（管理端）
     *
     * @param pageNum    页码
     * @param pageSize   每页数量
     * @param categoryId 分类ID
     * @param title      标题
     * @return 分页结果
     */
    IPage<Knowledge> page(Integer pageNum, Integer pageSize, Long categoryId, String title);

    /**
     * 分页查询科普（用户端）
     *
     * @param pageNum    页码
     * @param pageSize   每页数量
     * @param categoryId 分类ID
     * @param keyword    关键词
     * @return 分页结果
     */
    IPage<Knowledge> listEnabled(Integer pageNum, Integer pageSize, Long categoryId, String keyword);

    /**
     * 获取热门科普
     *
     * @param limit 数量限制
     * @return 科普列表
     */
    List<Knowledge> listHot(Integer limit);

    /**
     * 获取详情并增加浏览量
     *
     * @param id 科普ID
     * @return 科普详情
     */
    Knowledge getDetailAndIncrView(Long id);

    /**
     * 增加收藏量
     *
     * @param id    科普ID
     * @param delta 增量（正数增加，负数减少）
     */
    void incrCollectCount(Long id, int delta);
}
