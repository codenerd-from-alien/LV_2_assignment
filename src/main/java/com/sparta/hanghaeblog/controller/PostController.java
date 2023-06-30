package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PasswordDto;
import com.sparta.hanghaeblog.dto.SuccessResponse;
import com.sparta.hanghaeblog.service.PostService;
import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    // 글 목록 조회
    @GetMapping("")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/contents")
    public List<PostResponseDto> getPostsByKeyword(@RequestParam String keyword){
        return postService.getPostsByKeyword(keyword);
    }

    //특정 글 조회
    @GetMapping("/{id}")
    public PostResponseDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // 글 생성
    @PostMapping("")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 글 수정
    @PutMapping("/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // 글 삭제
    @DeleteMapping("/{id}")
    public SuccessResponse deletePost(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {
        postService.deletePost(id, passwordDto.getPassword());
        return new SuccessResponse("게시글이 삭제되었습니다.");
    }
}
