package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired //왜 이렇게 쓰는지 왜 지금상황에서 베스트인지 // 지금 상황에서 정말필요한건지
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional//추가했음 왜 추가했을까? @Transactional에대해서 알아보기
    public PostResponseDto createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(requestDto); //자바 레이어드 아키텍쳐 검색해보기 // 이 줄이 왜 문제인지 알 수 있음.// 왜 이 구조가 올바르지 않은지. //이 코드는 이 아키텍처에서 상당히 문제가 있는 코드다.
        // DB 저장
        Post savedPost = postRepository.save(post);
        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);//한줄로 만들기 의미없는 변수는 만들필요없음.
        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        //DB조회
        //디미터 법칙에 대해서 알아보기 // 가독성위반
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)//추가했음 왜 추가했을까? @Transactional에대해서 알아보기 // 읽기만하는 애들은 @Transactional(readOnly=true) 성능을위해서
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당ID의 게시글이 없습니다."));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPostsByKeyword(String keyword) {
        return postRepository.findAllByContentContainsIgnoreCaseOrderByModifiedAtDesc(keyword).stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        // 해당 글이 DB에 존재하는지 확인
        Post post = findPost(id);
        // post 내용 수정
        post.update(requestDto);
        return id;
    }

    public void deletePost(Long id, String password) {
        // 해당 글이 DB에 존재하는지 확인
        Post post = findPost(id); //객체지향이아님
        if (!post.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // post 삭제
        postRepository.delete(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 글은 존재하지 않습니다.")
        );
    }
}
