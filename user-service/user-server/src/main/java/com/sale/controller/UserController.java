package com.sale.controller;


import com.sale.response.ApiResponse;
import com.sale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("user_info")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/avatar")
    public ApiResponse<String> changeAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.ok(userService.changeAvatar(file));
    }
}
