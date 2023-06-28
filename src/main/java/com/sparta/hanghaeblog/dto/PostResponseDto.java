package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto { //reponse에 final을 왜 붙여도 될까? 자바 리플렉션과 관계가 있다.
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) { //정적팩토리 메소드패턴에 대해서 알아보기 public으로 생성자 안열고 정적팩토리 메서드를 쓴다. 객체를 생성할 수 있는 규칙에 맞춰서 여러 방법을 제공하는 방법.// 왜 이걸써야하나
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
//    public PostResponseDto getPostById(Long id) {
//        Post post = findPost(id);
//        return new PostResponseDto(post);
//    }
}
