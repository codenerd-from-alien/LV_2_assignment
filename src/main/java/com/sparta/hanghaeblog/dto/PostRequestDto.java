package com.sparta.hanghaeblog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor를 자주쓴다 -setter가 포함안되는것임
public class PostRequestDto { //final을 쓰면 안된다. 왜 안될까?
    private String title;
    private String content;
    private String author;
    private String password;
}
