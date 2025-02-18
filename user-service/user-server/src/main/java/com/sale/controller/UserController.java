package com.sale.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sale.constant.TokenConstant;
import com.sale.dto.UserInfoDto;
import com.sale.model.UserInfo;
import com.sale.response.ApiResponse;
import com.sale.service.IFileService;
import com.sale.service.TokenService;
import com.sale.service.UserService;
import com.sale.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("user_info")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return ApiResponse.ok();
    }

    @PostMapping("/avatar")
    public ApiResponse<String> changeAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.ok(userService.changeAvatar(file));
    }
}
