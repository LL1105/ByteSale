package com.sale.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

public class SecurityContextUtil {

    /**
     * 保存用户信息到 SecurityContext
     * @param authentication 认证信息
     */
    public static void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 获取当前登录用户的信息
     * @return UserDetails 用户详情
     */
    public static UserDetails getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (!Objects.isNull(authentication)) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获取当前用户的用户名
     * @return 用户名
     */
    public static String getCurrentUsername() {
        UserDetails userDetails = getCurrentUser();
        return userDetails != null ? userDetails.getUsername() : null;
    }

    /**
     * 获取认证信息
     * @return Authentication 认证信息
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 检查当前用户是否已认证
     * @return 是否认证
     */
    public static boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    /**
     * 清除当前用户信息
     */
    public static void clearContext() {
        SecurityContextHolder.clearContext();
    }
}
