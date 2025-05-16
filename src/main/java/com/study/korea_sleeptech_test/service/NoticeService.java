package com.study.korea_sleeptech_test.service;

import com.study.korea_sleeptech_test.dto.request.NoticeRequestDto;
import com.study.korea_sleeptech_test.dto.response.NoticeResponseDto;

import java.util.List;

public interface NoticeService {
    void createNotice(NoticeRequestDto requestDto, String username);
    List<NoticeResponseDto> getAllNotices();
}
