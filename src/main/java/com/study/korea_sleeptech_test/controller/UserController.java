package com.study.korea_sleeptech_test.controller;

import com.study.korea_sleeptech_test.dto.response.UserResponseDto;
import com.study.korea_sleeptech_test.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserResponseDto(user));
    }
}
