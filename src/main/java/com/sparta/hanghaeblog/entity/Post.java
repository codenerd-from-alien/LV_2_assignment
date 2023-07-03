package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false, length = 1000)
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public Post(PostRequestDto requestDto, String username) {
        this.username = username;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(PostRequestDto requestDto) {
        this.username = getUsername();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
