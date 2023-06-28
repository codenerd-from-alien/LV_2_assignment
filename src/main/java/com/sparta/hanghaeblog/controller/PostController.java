package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PasswordDto;
import com.sparta.hanghaeblog.dto.SuccessResponse;
import com.sparta.hanghaeblog.service.PostService;
import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//스프링 로그 4j검색해볼것 : 스프링에서 안전하게 로그를 찍는 방법. /왜 sout을 찍으면 안될까? 찾아보기
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
    public List<PostResponseDto> getPosts() { //List로하면 다른곳에서 수정이 가능하다. //List말고 PostReponseDto자체로 써서 명확하게 선언해주는걸 선호한다.
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
    } //생성과 수정은 역할을 분리하는게 좋다 생성과 수정의 대한 DTO를 분리하는게 좋다 PostCreateDto, PostUpdateDto를 분리하는게 좋다.

    // 글 수정
    @PutMapping("/posts/{id}") //putmapping과 fetch매핑 한번 검색해보기 상황에 따라 뭐가 맞는지
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) { //왜 래퍼클래스 Long을 썼는지 ; 힌트 : null을 받을 수 있냐 없냐? long으로 쓰면 null받을 수 없다. argument resolver관련된것.
        return postService.updatePost(id, requestDto);
    }

    // 글 삭제
    @DeleteMapping("/posts/{id}") //삭제방법에 대해서 다시 고민해보기, data.message가 삭제되었다는 방식으로
    public SuccessResponse deletePost(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {
        postService.deletePost(id, passwordDto.getPassword()); //왜 굳이 get을 써서 password를 가져왔는지
        return new SuccessResponse("게시글이 삭제되었습니다.");
    }
}
