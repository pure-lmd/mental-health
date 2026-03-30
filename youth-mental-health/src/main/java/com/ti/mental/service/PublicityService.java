package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Publicity;

/**
 * 心理宣传Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface PublicityService extends IService<Publicity> {

    /**
     * 分页查询宣传（管理端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param title    标题
     * @return 分页结果
     */
    IPage<Publicity> page(Integer pageNum, Integer pageSize, String title);

    /**
     * 分页查询宣传（用户端）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<Publicity> listEnabled(Integer pageNum, Integer pageSize);

    /**
     * 获取详情并增加浏览量
     *
     * @param id 宣传ID
     * @return 宣传详情
     */
    Publicity getDetailAndIncrView(Long id);
}
