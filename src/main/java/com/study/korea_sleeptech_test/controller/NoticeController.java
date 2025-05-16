package com.study.korea_sleeptech_test.controller;

import com.study.korea_sleeptech_test.dto.request.NoticeRequestDto;
import com.study.korea_sleeptech_test.entity.Notice;
import com.study.korea_sleeptech_test.entity.User;
import com.study.korea_sleeptech_test.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    // 공지사항 등록 (ADMIN만 가능)
    @PostMapping
    public ResponseEntity<?> createNotice(@RequestBody NoticeRequestDto requestDto,
                                          @AuthenticationPrincipal User user) {
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            return ResponseEntity.status(403).body("관리자만 공지사항을 작성할 수 있습니다.");
        }

        Notice notice = new Notice(requestDto.getTitle(), requestDto.getContent(), user);
        noticeRepository.save(notice);
        return ResponseEntity.ok("공지사항 등록 완료");
    }
}
