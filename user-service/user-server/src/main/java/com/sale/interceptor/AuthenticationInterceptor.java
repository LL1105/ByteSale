package com.sale.interceptor;

import com.sale.constant.Constant;
import com.sale.utils.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头中提取用户信息
        String userId = request.getHeader(Constant.HEADER_USER_ID);
        String username = request.getHeader(Constant.HEADER_USERNAME);

        UserContext.setUsername(username);
        UserContext.setUserId(userId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求处理完成后执行，无论是否发生异常，都要清理 ThreadLocal
        UserContext.remove();
    }
}
