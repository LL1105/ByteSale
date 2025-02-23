package com.sale.controller;

import com.sale.dto.UserInfoDto;
import com.sale.response.ApiResponse;
import com.sale.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TokenController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody UserInfoDto userInfoDto) {
        return ApiResponse.ok(loginService.register(userInfoDto));
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserInfoDto userInfoDto) {
        return ApiResponse.ok(loginService.login(userInfoDto.getUsername(), userInfoDto.getPassword()));
    }

    @PostMapping("/refresh")
    public ApiResponse<String> refresh() {
        return ApiResponse.ok(loginService.refresh());
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        loginService.logout();
        return ApiResponse.ok();
    }
}
