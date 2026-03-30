package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.Banner;
import com.ti.mental.mapper.BannerMapper;
import com.ti.mental.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public IPage<Banner> page(Integer pageNum, Integer pageSize) {
        Page<Banner> page = new Page<>(pageNum, pageSize);
        return this.lambdaQuery()
                .orderByAsc(Banner::getSort)
                .orderByDesc(Banner::getCreateTime)
                .page(page);
    }

    @Override
    public List<Banner> listEnabled() {
        return this.lambdaQuery()
                .eq(Banner::getStatus, Constants.STATUS_ENABLE)
                .orderByAsc(Banner::getSort)
                .list();
    }
}
