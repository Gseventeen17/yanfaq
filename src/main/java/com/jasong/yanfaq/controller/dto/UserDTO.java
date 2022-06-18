package com.jasong.yanfaq.controller.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String nickName;
    private String sex;
    private Integer age;
    private String role;
    private String token;
}
