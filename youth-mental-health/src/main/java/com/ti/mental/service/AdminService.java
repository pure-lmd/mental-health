package com.ti.mental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ti.mental.entity.Admin;

/**
 * 管理员Service接口
 *
 * @author Ti
 * @date 2026-02-03
 */
public interface AdminService extends IService<Admin> {

    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username, String password);

    /**
     * 分页查询管理员
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param username 用户名
     * @return 分页结果
     */
    IPage<Admin> page(Integer pageNum, Integer pageSize, String username);

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     */
    void add(Admin admin);

    /**
     * 更新管理员
     *
     * @param admin 管理员信息
     */
    void update(Admin admin);

    /**
     * 修改密码
     *
     * @param id          管理员ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Long id, String oldPassword, String newPassword);
}
