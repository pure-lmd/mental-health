package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Banner;

import java.util.List;

/**
 * 轮播图Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface BannerService extends IService<Banner> {

    /**
     * 分页查询轮播图
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<Banner> page(Integer pageNum, Integer pageSize);

    /**
     * 获取前台轮播图列表
     *
     * @return 轮播图列表
     */
    List<Banner> listEnabled();
}
