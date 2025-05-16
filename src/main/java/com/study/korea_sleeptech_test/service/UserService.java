package com.study.korea_sleeptech_test.service;

import com.study.korea_sleeptech_test.dto.request.SignupRequestDto;
import com.study.korea_sleeptech_test.dto.request.LoginRequestDto;
import com.study.korea_sleeptech_test.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto getMyInfo(String username);


    void signup(SignupRequestDto requestDto);
    String login(LoginRequestDto requestDto);
}
