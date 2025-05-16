package com.study.korea_sleeptech_test.repository;

import com.study.korea_sleeptech_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자명으로 사용자 찾기 (로그인 등에서 사용)
    Optional<User> findByUsername(String username);

    // 사용자명 중복 여부 확인 (회원가입 시 사용)
    boolean existsByUsername(String username);
}
