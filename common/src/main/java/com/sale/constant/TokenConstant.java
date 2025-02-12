package com.sale.constant;

public class TokenConstant {
    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz";

    /**
     *  用户ID
     */
    public static final String JWT_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    public static final String JWT_USERNAME = "username";

    /**
     * 用户名字段
     */
    public static final String JWT_EMAIL = "email";

    /**
     *  过期时间
     */
    public static final long EXPIRATION = 60 * 3600;
}