package com.ti.mental.common.utils;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 密码工具类
 *
 * @author Ti
 * @date 2026-02-03
 */
public class PasswordUtil {

    private PasswordUtil() {}

    /**
     * 密码加密（MD5）
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encrypt(String password) {
        return DigestUtil.md5Hex(password);
    }

    /**
     * 验证密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encrypt(rawPassword).equals(encodedPassword);
    }
}
