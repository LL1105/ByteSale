package com.sale.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sale.constant.TokenConstant;
import com.sale.dto.UserInfoDto;
import com.sale.model.UserInfo;
import com.sale.response.ApiResponse;
import com.sale.service.TokenService;
import com.sale.service.UserService;
import com.sale.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user_info")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return ApiResponse.ok();
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody UserInfoDto userInfoDto) {
        return ApiResponse.ok(userService.register(userInfoDto));
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserInfoDto userInfoDto) {
        UserInfo userInfo = userService.login(userInfoDto.getUsername(), userInfoDto.getPassword());
        String token = tokenService.createToken(userInfo);
        return ApiResponse.ok(token);
    }

    @PostMapping("/refresh")
    public ApiResponse<String> refresh(HttpServletRequest request) {
        String token = request.getHeader(TokenConstant.AUTHENTICATION);
        return ApiResponse.ok(tokenService.refreshToken(token));
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request) {
        String token = request.getHeader(TokenConstant.AUTHENTICATION);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUsername(token);
            // TODO: 删除用户缓存记录

        }
        return ApiResponse.ok();
    }
}
