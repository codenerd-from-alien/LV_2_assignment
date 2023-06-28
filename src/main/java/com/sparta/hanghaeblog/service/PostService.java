package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(requestDto);
        // DB 저장
        Post savedPost = postRepository.save(post);
        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        //DB조회
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

//    public List<PostResponseDto> getPostsByKeword(String keyword) {
//        return postRepository.findAllByTitleContainsOrContentContainsOrderByModifiedAtDesc(keyword).stream().map(PostResponseDto::new).toList();
//    }

    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
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
        Post post = findPost(id);
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
