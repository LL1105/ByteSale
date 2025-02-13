package com.sale.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilsTest {
    @Resource
    private RedisUtils redisUtils;

    @Test
    void setCacheObject() {
        redisUtils.setCacheObject("hello1", "world2");
    }

    @Test
    void deleteCacheObject() {
        redisUtils.deleteObject("don't exist");
    }
}