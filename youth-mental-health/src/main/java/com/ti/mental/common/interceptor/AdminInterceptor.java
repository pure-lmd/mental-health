package com.ti.mental.common.interceptor;

import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.exception.BusinessException;
import com.ti.mental.common.result.ResultCode;
import com.ti.mental.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;

/**
 * 管理员拦截器
 *
 * @author Ti
 * @date 2026-02-03
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取token
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (StrUtil.isBlank(token)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        // 验证token
        Claims claims = jwtUtil.parseToken(token);
        if (claims == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        // 验证角色
        String role = claims.get("role", String.class);
        if (!Constants.ROLE_ADMIN.equals(role)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        // 将用户信息存入request
        request.setAttribute(Constants.CURRENT_USER_ID, claims.get("userId", Long.class));
        request.setAttribute(Constants.CURRENT_USER_ROLE, role);
        return true;
    }
}
