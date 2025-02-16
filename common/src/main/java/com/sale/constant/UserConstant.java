package com.sale.constant;

import java.util.Arrays;
import java.util.List;

/**
 *  用户相关常量
 */
public class UserConstant {
    public static final Integer USERNAME_MIN_LENGTH = 5;

    public static final Integer USERNAME_MAX_LENGTH = 20;

    public static final Integer PASSWORD_MIN_LENGTH = 8;

    public static final Integer PASSWORD_MAX_LENGTH = 20;

    public static final Integer MAX_RETRY_COUNT = 3;

    public static final Long AVATAR_MAX_SIZE = 100 * 1024L;

    public static final List<String> AVATAR_TYPE = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/jpg"
    );
}
