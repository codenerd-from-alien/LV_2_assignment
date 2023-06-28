// PasswordDto.java
package com.sparta.hanghaeblog.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter //세터를 쓰면 안되는 이유에 대해서 고민해보기
//@AllArgsConstructor를 자주쓴다
public class PasswordDto {
    private String password;
}
