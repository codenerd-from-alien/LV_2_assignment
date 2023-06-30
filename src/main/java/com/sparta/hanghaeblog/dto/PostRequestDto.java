package com.sparta.hanghaeblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private String author;
    private String password;
}
