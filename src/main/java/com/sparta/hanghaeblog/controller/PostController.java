package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PasswordDto;
import com.sparta.hanghaeblog.dto.SuccessResponse;
import com.sparta.hanghaeblog.service.PostService;
import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor //final로 선언된 애들에 대해서 생성자를 직접 만들어준다.
@RestController
@RequestMapping("/api")//posts도 공통이니 posts도 추가할것
public class PostController {
    private final PostService postService; //무조건 생성자를 생성해주거나 바로 초기화해줘야함 & 객체의 주소값이 불변 //왜 이렇게 할까?

//    public PostController(PostService postService) { //생성자, DI(의존성 주입)의 역할을 한다.
//        this.postService = postService;
//    } -> @RequiredArgsConstructor로 정리될 수 있음

    // 글 목록 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/posts/contents")
    public List<PostResponseDto> getPostsByKeyword(@RequestParam String keyword){
        return postService.getPostsByKeyword(keyword);
    }

    //특정 글 조회
    @GetMapping("/posts/{id}")
    public PostResponseDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // 글 생성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 글 수정
    @PutMapping("/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // 글 삭제
    @DeleteMapping("/posts/{id}")
    public SuccessResponse deletePost(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {
        postService.deletePost(id, passwordDto.getPassword());
        return new SuccessResponse("게시글이 삭제되었습니다.");
    }
}
