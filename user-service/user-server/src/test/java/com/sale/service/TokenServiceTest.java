package com.sale.service;

import com.sale.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenServiceTest {
    @Autowired
    private TokenService tokenService;

    @Test
    void createToken() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.valueOf("13141") );
        userInfo.setUsername("1112313");
        userInfo.setEmail("qq.com");
        String token = tokenService.createToken(userInfo);
        System.out.println(token);
    }

    @Test
    void refreshToken() {
        String oldToken = "ayJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxMzE0MSwiZXhwIjoxNzM5NTQ2NTg2LCJpYXQiOjE3MzkzMzA1ODYsImVtYWlsIjoicXEuY29tIiwidXNlcm5hbWUiOiIxMTEyMzEzIn0.9UTqCB7jX-DUuGszXlcId6j7gtPLNYLAqaJPi50BCNE";
        String token = tokenService.refreshToken(oldToken);
        System.out.println(token);
    }
}