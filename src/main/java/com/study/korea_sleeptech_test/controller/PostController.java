package com.study.korea_sleeptech_test.controller;

import com.study.korea_sleeptech_test.dto.request.PostRequestDto;
import com.study.korea_sleeptech_test.dto.response.PostResponseDto;
import com.study.korea_sleeptech_test.entity.Post;
import com.study.korea_sleeptech_test.entity.User;
import com.study.korea_sleeptech_test.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    // 🔍 전체 게시글 조회 (비인증 허용)
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> list = postRepository.findAllWithAuthor().stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor().getUsername()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // 📄 단일 게시글 조회 (비인증 허용)
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        Post post = postRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        return ResponseEntity.ok(new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername()
        ));
    }

    // ✏ 게시글 작성 (USER, ADMIN 인증 필요)
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto,
                                                      @AuthenticationPrincipal User user) {
        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), user);
        postRepository.save(post);

        return ResponseEntity.ok(new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                user.getUsername()
        ));
    }

    // 🛠 게시글 수정 (작성자 본인만 가능)
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestBody PostRequestDto requestDto,
                                        @AuthenticationPrincipal User user) {
        Post post = postRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if (!post.getAuthor().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("작성자만 수정할 수 있습니다.");
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
        postRepository.save(post);

        return ResponseEntity.ok(new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername()
        ));
    }
}
