package com.study.korea_sleeptech_test.service;

import com.study.korea_sleeptech_test.dto.request.PostRequestDto;
import com.study.korea_sleeptech_test.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {
    void createPost(PostRequestDto requestDto, String username);
    PostResponseDto getPost(Long id);
    List<PostResponseDto> getAllPosts();
    void updatePost(Long id, PostRequestDto requestDto, String username);
}
