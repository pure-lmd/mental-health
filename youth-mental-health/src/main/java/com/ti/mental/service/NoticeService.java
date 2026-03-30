package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Notice;

import java.util.List;

/**
 * 通知公告Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface NoticeService extends IService<Notice> {

    /**
     * 分页查询通知公告
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param title    标题
     * @param type     类型
     * @return 分页结果
     */
    IPage<Notice> page(Integer pageNum, Integer pageSize, String title, Integer type);

    /**
     * 获取前台通知公告列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    IPage<Notice> listEnabled(Integer pageNum, Integer pageSize);

    /**
     * 获取最新公告
     *
     * @param limit 数量限制
     * @return 公告列表
     */
    List<Notice> listLatest(Integer limit);
}
