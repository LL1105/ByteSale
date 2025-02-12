package com.sale.utils;

import com.sale.constant.TokenConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.parser.Token;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        Date now = DateUtils.now();
        Date expiryDate = DateUtils.addSecond(now, (int)TokenConstant.EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)          // 设置签发时间
                .setExpiration(expiryDate) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, TokenConstant.SECRET) // 使用密钥签名
                .compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().
                setSigningKey(TokenConstant.SECRET).
                parseClaimsJws(token).
                getBody();
    }

    public static String getUsername(String token) {
        return parseToken(token).get(TokenConstant.JWT_USERNAME, String.class);
    }
}
