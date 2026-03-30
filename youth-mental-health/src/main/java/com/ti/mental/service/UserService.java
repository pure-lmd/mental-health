package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username, String password);

    /**
     * 分页查询用户
     *
     * @param pageNum      页码
     * @param pageSize     每页数量
     * @param username     用户名
     * @param healthStatus 健康状态
     * @return 分页结果
     */
    IPage<User> page(Integer pageNum, Integer pageSize, String username, Integer healthStatus);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    void update(User user);

    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 更新用户健康状态
     *
     * @param userId       用户ID
     * @param healthStatus 健康状态
     */
    void updateHealthStatus(Long userId, Integer healthStatus);

    /**
     * 统计各健康状态人数
     *
     * @return 统计结果
     */
    List<Map<String, Object>> countByHealthStatus();
}
