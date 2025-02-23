package com.sale.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sale.constant.CacheConstant;
import com.sale.constant.TokenConstant;
import com.sale.constant.UserConstant;
import com.sale.dto.UserInfoDto;
import com.sale.enums.BaseCode;
import com.sale.mapper.UserInfoMapper;
import com.sale.model.LoginUser;
import com.sale.model.UserInfo;
import com.sale.utils.RedisUtils;
import com.sale.utils.SecurityContextUtil;
import com.sale.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TokenService tokenService;

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    // TODO: 事务
    public String register(UserInfoDto userInfoDto) {
        // 1. 基本校验
        if (userInfoDto == null || !StringUtils.hasText(userInfoDto.getUsername()) ||
                !StringUtils.hasText(userInfoDto.getPassword())) {
            log.error("用户名和码不能为空");
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

    public String login(String username, String password) {
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

        // 2. 验证用户名密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3、获取用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 4、 生成Token
        String token = tokenService.createToken(loginUser.getUserInfo());
        redisUtils.setCacheObject(genAuthKey(loginUser.getUserInfo().getId()), loginUser, TokenConstant.EXPIRATION, TimeUnit.SECONDS);

        return token;
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUserInfo().getId();

        redisUtils.deleteObject(genAuthKey(userId));
    }

    public String refresh() {
        LoginUser loginUser = (LoginUser) SecurityContextUtil.getCurrentUser();
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException(BaseCode.USER_INFO_INVALID_LOGIN_STATUS.getMsg());
        }
        String token = tokenService.createToken(loginUser.getUserInfo());
        redisUtils.setCacheObject(genAuthKey(loginUser.getUserInfo().getId()), loginUser, TokenConstant.EXPIRATION, TimeUnit.SECONDS);
        return token;
    }

    private String genAuthKey(Long userId) {
        return CacheConstant.AUTH_PREFIX + userId;
    }
}
