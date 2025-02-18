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
    private IFileService fileService;

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
}
