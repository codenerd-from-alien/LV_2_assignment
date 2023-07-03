package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.repository.PostRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public PostService(PostRepository postRepository, JwtUtil jwtUtil) {
        this.postRepository = postRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, String tokenValue) {
        // RequestDto -> Entity
        Post post = new Post(requestDto, tokenUsername(tokenValue));
        if(!post.getUsername().equals(tokenUsername(tokenValue))){
            throw new IllegalArgumentException("작성은 불가능합니다.");
        }

        post = postRepository.save(post);
        // Entity -> ResponseDto
        return new PostResponseDto(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 글은 존재하지 않습니다.")
        );
    }
    private String tokenUsername(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);

        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }

        Claims claims = jwtUtil.getUserInfoFromToken(token);
        return claims.getSubject();
    }

    public List<PostResponseDto> getPosts() {

        return postRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당ID의 게시글이 없습니다."));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPostsByKeyword(String keyword) {
        return postRepository.findAllByContentContainsIgnoreCaseOrderByModifiedAtDesc(keyword).stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, String tokenValue) {
        // 해당 글이 DB에 존재하는지 확인
        Post post = findPost(id);
        if(!post.getUsername().equals(tokenUsername(tokenValue))){
            throw  new IllegalArgumentException("수정이 불가능합니다.");
        }
        // post 내용 수정
        post.update(requestDto);
        return new PostResponseDto(post);
    }


    @Transactional
    public void deletePost(Long id, String tokenValue) {
        // 해당 글이 DB에 존재하는지 확인
        Post post = findPost(id);
        if(!post.getUsername().equals(tokenUsername(tokenValue))){
            throw  new IllegalArgumentException("삭제가 불가능합니다.");
        }
        // post 삭제
        postRepository.delete(post);
    }


}
