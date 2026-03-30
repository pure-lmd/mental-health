package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Collect;

/**
 * 收藏Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface CollectService extends IService<Collect> {

    /**
     * 分页查询收藏（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param userId   用户ID
     * @param type     类型
     * @return 分页结果
     */
    IPage<Collect> page(Integer pageNum, Integer pageSize, Long userId, Integer type);

    /**
     * 添加收藏
     *
     * @param userId   用户ID
     * @param targetId 目标ID
     * @param type     类型
     */
    void add(Long userId, Long targetId, Integer type);

    /**
     * 取消收藏
     *
     * @param userId   用户ID
     * @param targetId 目标ID
     * @param type     类型
     */
    void remove(Long userId, Long targetId, Integer type);

    /**
     * 检查是否已收藏
     *
     * @param userId   用户ID
     * @param targetId 目标ID
     * @param type     类型
     * @return 是否已收藏
     */
    boolean hasCollected(Long userId, Long targetId, Integer type);

    /**
     * 获取用户收藏列表
     *
     * @param userId   用户ID
     * @param type     类型
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<Collect> listByUserId(Long userId, Integer type, Integer pageNum, Integer pageSize);
}
