package com.study.korea_sleeptech_test.dto.request;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String role; // "USER" 또는 "ADMIN"
}
