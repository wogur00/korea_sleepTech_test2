package com.study.korea_sleeptech_test.service.implementation;

import com.study.korea_sleeptech_test.dto.request.NoticeRequestDto;
import com.study.korea_sleeptech_test.dto.response.NoticeResponseDto;
import com.study.korea_sleeptech_test.entity.Notice;
import com.study.korea_sleeptech_test.entity.User;
import com.study.korea_sleeptech_test.repository.NoticeRepository;
import com.study.korea_sleeptech_test.repository.UserRepository;
import com.study.korea_sleeptech_test.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Override
    public void createNotice(NoticeRequestDto requestDto, String username) {
        User admin = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("관리자 계정 없음"));

        if (!"ADMIN".equals(admin.getRole())) {
            throw new SecurityException("관리자만 공지 등록 가능");
        }

        Notice notice = new Notice(
                requestDto.getTitle(),
                requestDto.getContent(),
                admin
        );

        noticeRepository.save(notice);
    }

    @Override
    public List<NoticeResponseDto> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(NoticeResponseDto::new)
                .toList();
    }
}
