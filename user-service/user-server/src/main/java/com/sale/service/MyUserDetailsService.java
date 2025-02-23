package com.sale.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sale.enums.BaseCode;
import com.sale.mapper.UserInfoMapper;
import com.sale.model.LoginUser;
import com.sale.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper =
                Wrappers.lambdaQuery(UserInfo.class).eq(UserInfo::getUsername, username);

        UserInfo userInfo = userInfoMapper.selectOne(userInfoLambdaQueryWrapper);
        if (Objects.isNull(userInfo)) {
            throw new RuntimeException(BaseCode.USER_INFO_USERNAME_IS_NOT_EXIST.getMsg());
        }
        return new LoginUser(userInfo);

    }
}
