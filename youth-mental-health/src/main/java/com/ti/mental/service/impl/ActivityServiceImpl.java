package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.entity.Activity;
import com.ti.mental.mapper.ActivityMapper;
import com.ti.mental.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Override
    public IPage<Activity> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        Page<Activity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(title), Activity::getTitle, title)
                .eq(status != null, Activity::getStatus, status)
                .orderByDesc(Activity::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public IPage<Activity> listEnabled(Integer pageNum, Integer pageSize) {
        Page<Activity> page = new Page<>(pageNum, pageSize);
        return this.lambdaQuery()
                .gt(Activity::getStatus, 0)
                .orderByDesc(Activity::getIsHot)
                .orderByDesc(Activity::getCreateTime)
                .page(page);
    }

    @Override
    public List<Activity> listHot(Integer limit) {
        return this.lambdaQuery()
                .gt(Activity::getStatus, 0)
                .eq(Activity::getIsHot, 1)
                .orderByDesc(Activity::getCreateTime)
                .last("LIMIT " + limit)
                .list();
    }

    @Override
    public void incrCurrentNum(Long id) {
        this.lambdaUpdate()
                .eq(Activity::getId, id)
                .setSql("current_num = current_num + 1")
                .update();
    }

    @Override
    public void decrCurrentNum(Long id) {
        this.lambdaUpdate()
                .eq(Activity::getId, id)
                .setSql("current_num = GREATEST(current_num - 1, 0)")
                .update();
    }

    @Override
    public List<java.util.Map<String, Object>> getActivityRegistrationStats() {
        return this.baseMapper.getActivityRegistrationStats();
    }
}
