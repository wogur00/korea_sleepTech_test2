package com.study.korea_sleeptech_test.service.implementation;

import com.study.korea_sleeptech_test.dto.request.SignupRequestDto;
import com.study.korea_sleeptech_test.dto.request.LoginRequestDto;
import com.study.korea_sleeptech_test.dto.response.UserResponseDto;
import com.study.korea_sleeptech_test.entity.User;
import com.study.korea_sleeptech_test.jwt.JwtUtil;
import com.study.korea_sleeptech_test.repository.UserRepository;
import com.study.korea_sleeptech_test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void signup(SignupRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        String role = requestDto.getRole().toUpperCase(); // "USER" or "ADMIN"
        User user = new User(requestDto.getUsername(), encodedPassword, role);

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }

    @Override
    public UserResponseDto getMyInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new UserResponseDto(user);
    }
}
