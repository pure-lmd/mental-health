package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.Notice;
import com.ti.mental.mapper.NoticeMapper;
import com.ti.mental.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知公告Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public IPage<Notice> page(Integer pageNum, Integer pageSize, String title, Integer type) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(title), Notice::getTitle, title)
                .eq(type != null, Notice::getType, type)
                .orderByDesc(Notice::getIsTop)
                .orderByDesc(Notice::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public IPage<Notice> listEnabled(Integer pageNum, Integer pageSize) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        return this.lambdaQuery()
                .eq(Notice::getStatus, Constants.STATUS_ENABLE)
                .orderByDesc(Notice::getIsTop)
                .orderByDesc(Notice::getCreateTime)
                .page(page);
    }

    @Override
    public List<Notice> listLatest(Integer limit) {
        return this.lambdaQuery()
                .eq(Notice::getStatus, Constants.STATUS_ENABLE)
                .orderByDesc(Notice::getIsTop)
                .orderByDesc(Notice::getCreateTime)
                .last("LIMIT " + limit)
                .list();
    }
}
