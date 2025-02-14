package com.sale.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sale.constant.CacheConstant;
import com.sale.constant.UserConstant;
import com.sale.dto.UserInfoDto;
import com.sale.enums.BaseCode;
import com.sale.mapper.UserInfoMapper;
import com.sale.model.UserInfo;
import com.sale.utils.RedisUtils;
import com.sale.utils.SecurityUtils;
import com.sale.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IFileService fileService;

    @Resource
    private RedisUtils redisUtils;

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

    public String changeAvatar(MultipartFile file) throws IOException {
        // 1、 上传文件
        if(file.isEmpty()) {
            log.error("上传头像文件为空");
            throw new RuntimeException(BaseCode.FILE_IS_EMPTY.getMsg());
        }

        if(file.getSize()> UserConstant.AVATAR_MAX_SIZE) {
            log.error("上传头像文件超出大小");
            throw new RuntimeException(BaseCode.FILE_SIZE_EXCEED.getMsg());
        }

        if(!UserConstant.AVATAR_TYPE.contains(file.getContentType())) {
            log.error("文件类型不符合规范");
            throw new RuntimeException(BaseCode.FILE_TYPE_ILLEGAL.getMsg());
        }

        String avatarUrl = fileService.upload(file);
        String username = UserContext.getUsername();
        if(userInfoMapper.updateAvatarByUsername(username, avatarUrl) == 0) {
            log.error("修改头像失败");
            throw new RuntimeException(BaseCode.USER_INFO_AVATAR_CHANGE_FAILED.getMsg());
        }

        return avatarUrl;
    }

    public void validatePassword(String password, UserInfo userInfo) {
        String username = userInfo.getUsername();
        Integer retryCount = redisUtils.getCacheObject(genUsernameKey(username));

        if(Objects.isNull(retryCount)) {
            retryCount = 0;
        }

        if(retryCount >= UserConstant.MAX_RETRY_COUNT) {
            String errMsg = String.format("密码输入错误 %s 次，帐户锁定 %d 分钟", retryCount, CacheConstant.LOGIN_RETRY_PERIOD);
            log.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        if(!SecurityUtils.matchesPassword(password, userInfo.getPassword())) {
            retryCount += 1;
            redisUtils.setCacheObject(genUsernameKey(username), retryCount, CacheConstant.LOGIN_RETRY_PERIOD, TimeUnit.MINUTES);
            log.error("密码输入错误");
            throw new RuntimeException(BaseCode.USER_INFO_PASSWORD_NOT_MATCH.getMsg());
        } else {
            redisUtils.deleteObject(genUsernameKey(username));
        }
    }

    private String genUsernameKey(String username) {
        return CacheConstant.LOGIN_RETRY_KEY + username;
    }
}
