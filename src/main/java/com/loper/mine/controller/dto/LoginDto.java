package com.loper.mine.controller.dto;

import com.loper.mine.common.annotions.Password;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author liaonanzhou
 * @date 2021/6/4 10:09
 * @description
 **/
@Data
public class LoginDto {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 6, max = 16, message = "用户名格式不正确")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 16, message = "密码格式不正确")
    @Password
    private String password;


    public static void main(String[] args) {

    }
}
