package com.sale.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserContext {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static final String CONTEXT_KEY_USERNAME = "username";

    public static final String CONTEXT_KEY_USER_ID = "user_id";

    /**
     * 设置数据
     *
     * @param key 键
     * @param value 值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if(Objects.isNull(map)) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    /**
     * 获取数据
     * @param key 键
     * @return value 值
     */
    public static Object get(String key) {
        Map<String,Object> map = threadLocal.get();
        if(Objects.isNull(map)) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 移除数据
     */
    public static void remove() {
        threadLocal.remove();
    }

    public static void setUsername(String username) {
        set(CONTEXT_KEY_USERNAME, username);
    }

    public static String getUsername() {
        return (String) get(CONTEXT_KEY_USERNAME);
    }

    public static void setUserId(String userId) {
        set(CONTEXT_KEY_USER_ID, userId);
    }

    public static String getUserId() {
        return (String) get(CONTEXT_KEY_USER_ID);
    }
}
