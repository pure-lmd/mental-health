package com.ti.mental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.exception.BusinessException;
import com.ti.mental.common.result.ResultCode;
import com.ti.mental.common.utils.JwtUtil;
import com.ti.mental.common.utils.PasswordUtil;
import com.ti.mental.entity.Admin;
import com.ti.mental.mapper.AdminMapper;
import com.ti.mental.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 管理员Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public String login(String username, String password) {
        // 查询管理员
        Admin admin = this.lambdaQuery()
                .eq(Admin::getUsername, username)
                .one();
        if (admin == null) {
            throw new BusinessException(ResultCode.LOGIN_ERROR);
        }
        // 验证密码
        if (!PasswordUtil.matches(password, admin.getPassword())) {
            throw new BusinessException(ResultCode.LOGIN_ERROR);
        }
        // 验证状态
        if (!Constants.STATUS_ENABLE.equals(admin.getStatus())) {
            throw new BusinessException(ResultCode.ACCOUNT_DISABLED);
        }
        // 生成token
        return jwtUtil.generateToken(admin.getId(), Constants.ROLE_ADMIN);
    }

    @Override
    public IPage<Admin> page(Integer pageNum, Integer pageSize, String username) {
        Page<Admin> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(username), Admin::getUsername, username)
                .orderByDesc(Admin::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public void add(Admin admin) {
        // 检查用户名是否存在
        long count = this.lambdaQuery()
                .eq(Admin::getUsername, admin.getUsername())
                .count();
        if (count > 0) {
            throw new BusinessException(ResultCode.USERNAME_EXISTS);
        }
        // 加密密码
        admin.setPassword(PasswordUtil.encrypt(admin.getPassword()));
        admin.setStatus(Constants.STATUS_ENABLE);
        this.save(admin);
    }

    @Override
    public void update(Admin admin) {
        // 不更新密码
        admin.setPassword(null);
        this.updateById(admin);
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        Admin admin = this.getById(id);
        if (admin == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 验证旧密码
        if (!PasswordUtil.matches(oldPassword, admin.getPassword())) {
            throw new BusinessException(ResultCode.OLD_PASSWORD_ERROR);
        }
        // 更新密码
        admin.setPassword(PasswordUtil.encrypt(newPassword));
        this.updateById(admin);
    }
}
