package com.ti.mental.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 *
 * @author Ti
 * @date 2026-02-03
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    FAIL(400, "操作失败"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权，请先登录"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 服务器错误
     */
    ERROR(500, "服务器错误"),

    /**
     * 参数错误
     */
    PARAM_ERROR(1001, "参数错误"),

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS(1002, "用户名已存在"),

    /**
     * 用户名或密码错误
     */
    LOGIN_ERROR(1003, "用户名或密码错误"),

    /**
     * 账号已被禁用
     */
    ACCOUNT_DISABLED(1004, "账号已被禁用"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(1005, "文件上传失败"),

    /**
     * 文件类型不支持
     */
    FILE_TYPE_ERROR(1006, "文件类型不支持"),

    /**
     * 文件大小超出限制
     */
    FILE_SIZE_ERROR(1007, "文件大小超出限制"),

    /**
     * 已收藏
     */
    ALREADY_COLLECTED(1008, "已收藏"),

    /**
     * 已报名
     */
    ALREADY_REGISTERED(1009, "已报名该活动"),

    /**
     * 活动人数已满
     */
    ACTIVITY_FULL(1010, "活动报名人数已满"),

    /**
     * 旧密码错误
     */
    OLD_PASSWORD_ERROR(1011, "旧密码错误");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;
}
