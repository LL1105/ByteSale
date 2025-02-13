package com.sale.service;

import com.sale.dto.UserInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void register() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUsername("for-test1");
        userInfoDto.setPassword("for-test1");
        userService.register(userInfoDto);
    }

    @Test
    void login() {
        String[] retries = new String[] {"for-test1", "for-test", "for-test", "for-test"};
        for(String pwd : retries) {
            try {
                userService.login("for-test1", pwd);
            } catch (Exception e) {
                System.out.println("====");
            }
            return;
        }
    }
}