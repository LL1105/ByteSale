package com.sale.service;

import com.sale.model.UserInfo;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void logout() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxMzE0MSwidXNlcl9rZXkiOiJiZmQyY2FiOS01NjVlLTQyNDItODVlMi1mOGU1ZjczZDVjZmEiLCJleHAiOjE3Mzk2NTE1OTAsImlhdCI6MTczOTQzNTU5MCwiZW1haWwiOiJxcS5jb20iLCJ1c2VybmFtZSI6IjExMTIzMTMifQ.W7imSm9JWWACOIm8Qz9bIsNBqBgVdHurF0J0zw6OTCk";
        tokenService.delToken(token);
    }
}