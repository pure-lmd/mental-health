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
import com.ti.mental.entity.User;
import com.ti.mental.mapper.UserMapper;
import com.ti.mental.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void register(User user) {
        // 检查用户名是否存在
        long count = this.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .count();
        if (count > 0) {
            throw new BusinessException(ResultCode.USERNAME_EXISTS);
        }
        // 加密密码
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        user.setStatus(Constants.STATUS_ENABLE);
        user.setHealthStatus(Constants.HEALTH_STATUS_HEALTHY);
        this.save(user);
    }

    @Override
    public String login(String username, String password) {
        // 查询用户
        User user = this.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
        if (user == null) {
            throw new BusinessException(ResultCode.LOGIN_ERROR);
        }
        // 验证密码
        if (!PasswordUtil.matches(password, user.getPassword())) {
            throw new BusinessException(ResultCode.LOGIN_ERROR);
        }
        // 验证状态
        if (!Constants.STATUS_ENABLE.equals(user.getStatus())) {
            throw new BusinessException(ResultCode.ACCOUNT_DISABLED);
        }
        // 生成token
        return jwtUtil.generateToken(user.getId(), Constants.ROLE_USER);
    }

    @Override
    public IPage<User> page(Integer pageNum, Integer pageSize, String username, Integer healthStatus) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(username), User::getUsername, username)
                .eq(healthStatus != null, User::getHealthStatus, healthStatus)
                .orderByDesc(User::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public void update(User user) {
        // 不更新密码
        user.setPassword(null);
        this.updateById(user);
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 验证旧密码
        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.OLD_PASSWORD_ERROR);
        }
        // 更新密码
        user.setPassword(PasswordUtil.encrypt(newPassword));
        this.updateById(user);
    }

    @Override
    public void updateHealthStatus(Long userId, Integer healthStatus) {
        User user = new User();
        user.setId(userId);
        user.setHealthStatus(healthStatus);
        this.updateById(user);
    }

    @Override
    public List<Map<String, Object>> countByHealthStatus() {
        return baseMapper.countByHealthStatus();
    }
}
