package com.sale.service;

import com.sale.constant.CacheConstant;
import com.sale.constant.TokenConstant;
import com.sale.enums.BaseCode;
import com.sale.model.UserInfo;
import com.sale.utils.JwtUtils;
import com.sale.utils.RedisUtils;
import com.sale.utils.UUID;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenService {
    @Resource
    private RedisUtils redisUtils;

    public String createToken(UserInfo userInfo) {
        Map<String, Object> playload = new HashMap<String, Object>();
        String userKey = UUID.fastUUID().toString();
        playload.put(TokenConstant.JWT_USER_KEY, userKey);
        playload.put(TokenConstant.JWT_USER_ID, userInfo.getId());
        playload.put(TokenConstant.JWT_USERNAME, userInfo.getUsername());
        playload.put(TokenConstant.JWT_EMAIL, userInfo.getEmail());

        String token = JwtUtils.createToken(playload);

        redisUtils.setCacheObject(genTokenKey(userKey), userKey, TokenConstant.EXPIRATION*2, TimeUnit.SECONDS);

        return token;
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

    public void delToken(String token) {
        String userKey = JwtUtils.getUserKey(token);
        redisUtils.deleteObject(genTokenKey(userKey));
    }

    private String genTokenKey(String userKey) {
        return CacheConstant.TOKEN_PREFIX + userKey;
    }


}
