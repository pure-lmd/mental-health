package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.Publicity;
import com.ti.mental.mapper.PublicityMapper;
import com.ti.mental.service.PublicityService;
import org.springframework.stereotype.Service;

/**
 * 心理宣传Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class PublicityServiceImpl extends ServiceImpl<PublicityMapper, Publicity> implements PublicityService {

    @Override
    public IPage<Publicity> page(Integer pageNum, Integer pageSize, String title) {
        Page<Publicity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Publicity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(title), Publicity::getTitle, title)
                .orderByAsc(Publicity::getSort)
                .orderByDesc(Publicity::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public IPage<Publicity> listEnabled(Integer pageNum, Integer pageSize) {
        Page<Publicity> page = new Page<>(pageNum, pageSize);
        return this.lambdaQuery()
                .eq(Publicity::getStatus, Constants.STATUS_ENABLE)
                .orderByAsc(Publicity::getSort)
                .orderByDesc(Publicity::getCreateTime)
                .page(page);
    }

    @Override
    public Publicity getDetailAndIncrView(Long id) {
        Publicity publicity = this.getById(id);
        if (publicity != null) {
            // 增加浏览量
            this.lambdaUpdate()
                    .eq(Publicity::getId, id)
                    .setSql("view_count = view_count + 1")
                    .update();
            publicity.setViewCount(publicity.getViewCount() + 1);
        }
        return publicity;
    }
}
