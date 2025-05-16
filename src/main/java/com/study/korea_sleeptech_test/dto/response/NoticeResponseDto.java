package com.study.korea_sleeptech_test.dto.response;

import com.study.korea_sleeptech_test.entity.Notice;
import lombok.Getter;

@Getter
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private String admin;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.admin = notice.getAdmin().getUsername();
    }
}
