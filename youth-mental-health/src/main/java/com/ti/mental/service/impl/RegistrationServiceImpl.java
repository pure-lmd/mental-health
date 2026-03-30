package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.exception.BusinessException;
import com.ti.mental.common.result.ResultCode;
import com.ti.mental.entity.Activity;
import com.ti.mental.entity.Registration;
import com.ti.mental.mapper.RegistrationMapper;
import com.ti.mental.service.ActivityService;
import com.ti.mental.service.RegistrationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 活动报名Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Resource
    private ActivityService activityService;

    @Override
    public IPage<Registration> page(Integer pageNum, Integer pageSize, Long activityId, Integer status) {
        Page<Registration> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithInfo(page, null, activityId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(Long userId, Long activityId, String remark) {
        // 检查是否已报名
        if (hasRegistered(userId, activityId)) {
            throw new BusinessException(ResultCode.ALREADY_REGISTERED);
        }
        // 检查活动是否存在且可报名
        Activity activity = activityService.getById(activityId);
        if (activity == null || activity.getStatus() != 1) {
            throw new BusinessException("活动不存在或不在报名中");
        }
        // 检查人数
        if (activity.getCurrentNum() >= activity.getMaxNum()) {
            throw new BusinessException(ResultCode.ACTIVITY_FULL);
        }
        // 创建报名记录
        Registration registration = new Registration();
        registration.setUserId(userId);
        registration.setActivityId(activityId);
        registration.setRemark(remark);
        registration.setStatus(Constants.REGISTRATION_STATUS_PENDING);
        this.save(registration);
        // 增加报名人数
        activityService.incrCurrentNum(activityId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long id, Integer status, String auditRemark) {
        Registration registration = this.getById(id);
        if (registration == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 如果是拒绝，减少报名人数
        if (Constants.REGISTRATION_STATUS_REJECTED.equals(status) &&
                Constants.REGISTRATION_STATUS_PENDING.equals(registration.getStatus())) {
            activityService.decrCurrentNum(registration.getActivityId());
        }
        registration.setStatus(status);
        registration.setAuditRemark(auditRemark);
        registration.setAuditTime(LocalDateTime.now());
        this.updateById(registration);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long id, Long userId) {
        Registration registration = this.getById(id);
        if (registration == null || !registration.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 只能取消待审核或已通过的报名
        if (!Constants.REGISTRATION_STATUS_PENDING.equals(registration.getStatus()) &&
                !Constants.REGISTRATION_STATUS_APPROVED.equals(registration.getStatus())) {
            throw new BusinessException("当前状态不可取消");
        }
        registration.setStatus(Constants.REGISTRATION_STATUS_CANCELLED);
        this.updateById(registration);
        // 减少报名人数
        activityService.decrCurrentNum(registration.getActivityId());
    }

    @Override
    public IPage<Registration> listByUserId(Long userId, Integer pageNum, Integer pageSize) {
        Page<Registration> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithInfo(page, userId, null, null);
    }

    @Override
    public boolean hasRegistered(Long userId, Long activityId) {
        return this.lambdaQuery()
                .eq(Registration::getUserId, userId)
                .eq(Registration::getActivityId, activityId)
                .ne(Registration::getStatus, Constants.REGISTRATION_STATUS_CANCELLED)
                .ne(Registration::getStatus, Constants.REGISTRATION_STATUS_REJECTED)
                .count() > 0;
    }
}
