package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.dto.SuccessResponse;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.service.PostService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;

    // 글 목록 조회
    @GetMapping("")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    //특정키워드 글조회
    @GetMapping("/contents")
    public List<PostResponseDto> getPostsByKeyword(@RequestParam String keyword) {
        return postService.getPostsByKeyword(keyword);
    }

    //특정 글 조회
    @GetMapping("/{id}")
    public PostResponseDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // 글 생성
    @PostMapping("")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return postService.createPost(requestDto, tokenValue);
    }

    // 글 수정
    @PutMapping("/{id}")
    public PostResponseDto  updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return postService.updatePost(id, requestDto, tokenValue);
    }

    // 글 삭제
    @DeleteMapping("/{id}")
    public SuccessResponse deletePost(@PathVariable Long id, @RequestHeader("Authorization") @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        postService.deletePost(id, tokenValue);
        return new SuccessResponse("게시글이 삭제되었습니다.");
    }
}
