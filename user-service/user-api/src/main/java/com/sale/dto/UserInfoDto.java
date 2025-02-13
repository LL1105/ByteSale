package com.sale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserInfoDto {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 20, message = "用户名长度需在5-20位之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max=20, message = "用户名长度需在8-20位之间")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,20}$",
            message = "密码需包含大小写字母和数字，长度8-20位")
    private String password;

    private String avatar;

    private String email;
}
