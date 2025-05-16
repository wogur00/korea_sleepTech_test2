package com.study.korea_sleeptech_test.repository;

import com.study.korea_sleeptech_test.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
