package com.sale.mapper;

import com.sale.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserInfoTest {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void createUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("wuji");
        userInfo.setPassword("12345");
        userInfo.setEmail("wuji");

        // 插入用户信息
        int result = userInfoMapper.insert(userInfo);
        if(result == 1) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }
}
