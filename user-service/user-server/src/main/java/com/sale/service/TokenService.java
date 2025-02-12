package com.sale.service;

import com.sale.constant.TokenConstant;
import com.sale.enums.BaseCode;
import com.sale.model.UserInfo;
import com.sale.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class TokenService {
    public String createToken(UserInfo userInfo) {
        Map<String, Object> playload = new HashMap<String, Object>();
        playload.put(TokenConstant.JWT_USER_ID, userInfo.getId());
        playload.put(TokenConstant.JWT_USERNAME, userInfo.getUsername());
        playload.put(TokenConstant.JWT_EMAIL, userInfo.getEmail());

        return JwtUtils.createToken(playload);
    }

    public String refreshToken(String token) {
        try {
            Claims claims = JwtUtils.parseToken(token);
            if (!Objects.isNull(claims)) {
                Long userId = claims.get(TokenConstant.JWT_USER_ID, Long.class);
                String username = claims.get(TokenConstant.JWT_USERNAME, String.class);
                String email = claims.get(TokenConstant.JWT_EMAIL, String.class);

                // 2. 构造 UserInfo 对象
                UserInfo userInfo = new UserInfo();
                userInfo.setId(userId);
                userInfo.setUsername(username);
                userInfo.setEmail(email);

                // 3. 使用 UserInfo 对象创建新的 Token
                return createToken(userInfo);
            } else {
                log.error("无效的token");
                throw new RuntimeException(BaseCode.USER_INFO_INVALID_TOKEN.getMsg());
            }
        } catch (Exception e) {
            log.error("无效的token");
            throw new RuntimeException(BaseCode.USER_INFO_INVALID_TOKEN.getMsg());
        }
    }
}
