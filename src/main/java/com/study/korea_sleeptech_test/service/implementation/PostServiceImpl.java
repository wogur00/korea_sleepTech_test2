package com.study.korea_sleeptech_test.service.implementation;

import com.study.korea_sleeptech_test.dto.request.PostRequestDto;
import com.study.korea_sleeptech_test.dto.response.PostResponseDto;
import com.study.korea_sleeptech_test.entity.Post;
import com.study.korea_sleeptech_test.entity.User;
import com.study.korea_sleeptech_test.repository.PostRepository;
import com.study.korea_sleeptech_test.repository.UserRepository;
import com.study.korea_sleeptech_test.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void createPost(PostRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("작성자 없음"));

        Post post = new Post(
                requestDto.getTitle(),
                requestDto.getContent(),
                user
        );

        postRepository.save(post);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        // Lazy 로딩 예외 방지를 위해 세션 내에서 authorName 꺼내기
        String authorName = post.getAuthor().getUsername();

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), authorName);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> {
                    String authorName = post.getAuthor().getUsername(); // Lazy 방지
                    return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), authorName);
                })
                .toList();
    }

    @Override
    public void updatePost(Long id, PostRequestDto requestDto, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new SecurityException("작성자만 수정 가능");
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
        postRepository.save(post);
    }
}
