package com.sale.filter;

import com.sale.constant.CacheConstant;
import com.sale.constant.Constant;
import com.sale.enums.BaseCode;
import com.sale.model.LoginUser;
import com.sale.utils.RedisUtils;
import com.sale.utils.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 1、获取user key
        String userkey = httpServletRequest.getHeader(Constant.HEADER_USERKEY);

        // 后面过滤器拦截
        if(Objects.isNull(userkey)) {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        // 2、 获取登录信息
        LoginUser loginUser = redisUtils.getCacheObject(genAuthKey(userkey));
        if(Objects.isNull(loginUser)) {
            throw new RuntimeException(BaseCode.USER_INFO_NOT_LOGIN.getMsg());
        }

        // 3、将获取到的用户信息存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextUtil.setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String genAuthKey(String userKey) {
        return CacheConstant.AUTH_PREFIX + userKey;
    }
}
