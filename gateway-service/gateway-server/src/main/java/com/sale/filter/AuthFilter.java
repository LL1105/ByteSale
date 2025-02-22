package com.sale.filter;


import com.sale.config.properies.IgnoreWhiteProterties;
import com.sale.constant.Constant;
import com.sale.constant.TokenConstant;
import com.sale.enums.BaseCode;
import com.sale.utils.JwtUtils;
import com.sale.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IgnoreWhiteProterties ignoreWhite;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 白名单
        String url = request.getURI().getPath();
        if(isMatch(url)) {
            return chain.filter(exchange);
        }

        // 身份校验
        String token = request.getHeaders().getFirst(TokenConstant.AUTHENTICATION);
        Claims claims = JwtUtils.parseToken(token);
        if (Objects.isNull(claims)) {
            throw new RuntimeException(BaseCode.USER_INFO_INVALID_TOKEN.getMsg());
        }
        String username = (String) claims.get(TokenConstant.JWT_USERNAME);
        String user_id = (String) claims.get(TokenConstant.JWT_USER_ID);

        if (isLogin(username)){
            throw new RuntimeException(BaseCode.USER_INFO_LOGIN_EXPIRED.getMsg());
        }

        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header(Constant.HEADER_USERNAME, username);
        mutate.header(Constant.HEADER_USER_ID, user_id);

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return -200;
    }

    private Boolean isMatch(String url) {
        if(Objects.isNull(url))
            return false;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for(String pattern : ignoreWhite.getWhites()) {
            if(antPathMatcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLogin(String username) {
        return redisUtils.hasKey(username);
    }
}
