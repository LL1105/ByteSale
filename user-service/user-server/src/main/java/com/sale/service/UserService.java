package com.sale.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sale.constant.UserConstant;
import com.sale.dto.UserInfoDto;
import com.sale.enums.BaseCode;
import com.sale.mapper.UserInfoMapper;
import com.sale.model.UserInfo;
import com.sale.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TokenService tokenService;

    // TODO: 事务
    public String register(UserInfoDto userInfoDto) {
        // 1. 基本校验
        if (userInfoDto == null || !StringUtils.hasText(userInfoDto.getUsername()) ||
                !StringUtils.hasText(userInfoDto.getPassword())) {
            log.error("用户名和密码不能为空");
            throw new RuntimeException(BaseCode.USER_INFO_USERNAME_OR_PASSWORD_IS_NULL.getMsg());
        }
        // 1.1 用户名和密码的校验
        String username = userInfoDto.getUsername();
        String password = userInfoDto.getPassword();
        // TODO: 加强校验
        if (username.length() < UserConstant.USERNAME_MIN_LENGTH
                || username.length() > UserConstant.USERNAME_MAX_LENGTH) {
            log.error("账户长度必须在5到20个字符之间");
            throw new RuntimeException(BaseCode.USER_INFO_INVALID_USERNAME.getMsg());
        }
        if (password.length() < UserConstant.PASSWORD_MIN_LENGTH
                || password.length() > UserConstant.PASSWORD_MAX_LENGTH) {
            log.error("密码长度必须在8到20个字符之间");
            throw new RuntimeException(BaseCode.USER_INFO_INVALID_PASSWORD.getMsg());
        }

        // 2. 检查用户名是否已存在
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper =
                Wrappers.lambdaQuery(UserInfo.class).eq(UserInfo::getUsername, userInfoDto.getUsername());

        UserInfo existingUser = userInfoMapper.selectOne(userInfoLambdaQueryWrapper);
        if (!Objects.isNull(existingUser)) {
           log.error("用户名 {} 已存在", userInfoDto.getUsername());
           throw new RuntimeException(BaseCode.USER_INFO_USERNAME_IS_EXIST.getMsg());
        }

        // 3. 对密码进行加密
        String encodedPassword = SecurityUtils.encryptPassword(password);

        // 4. 创建用户对象并保存到数据库
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoDto.getUsername());
        userInfo.setPassword(encodedPassword);

        if(userInfoMapper.insert(userInfo) != 1) {
            log.error("创建用户失败");
            throw new RuntimeException(BaseCode.SYSTEM_ERROR.getMsg());
        }

        return "创建成功";
    }

    public UserInfo login(String username, String password) {
        // 1、 基本校验
        if (username.length() < UserConstant.USERNAME_MIN_LENGTH
                || username.length() > UserConstant.USERNAME_MAX_LENGTH) {
            log.error("账户长度必须在5到20个字符之间");
            throw new RuntimeException(BaseCode.USER_INFO_INVALID_USERNAME.getMsg());
        }
        if (password.length() < UserConstant.PASSWORD_MIN_LENGTH
                || password.length() > UserConstant.PASSWORD_MAX_LENGTH) {
            log.error("密码长度必须在8到20个字符之间");
            throw new RuntimeException(BaseCode.USER_INFO_INVALID_PASSWORD.getMsg());
        }

        // 2. 查询用户信息
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper =
                Wrappers.lambdaQuery(UserInfo.class).eq(UserInfo::getUsername, username);

        UserInfo userInfo = userInfoMapper.selectOne(userInfoLambdaQueryWrapper);
        if (Objects.isNull(userInfo)) {
            log.error("用户名 {} 不存在", username);
            throw new RuntimeException(BaseCode.USER_INFO_USERNAME_IS_NOT_EXIST.getMsg());
        }

        // 3. 密码验证
        validatePassword(password, userInfo);

        return userInfo;
    }

    public static void validatePassword(String password, UserInfo userInfo) {
        // TODO: 重试次数
        if(!SecurityUtils.matchesPassword(password, userInfo.getPassword())) {
            throw new RuntimeException(BaseCode.USER_INFO_PASSWORD_NOT_MATCH.getMsg());
        }
    }
}
