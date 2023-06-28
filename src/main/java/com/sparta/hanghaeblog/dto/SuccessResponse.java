package com.sparta.hanghaeblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//@require
public class SuccessResponse {
    private String message; //final이 붙어있어야한다. 왜일까
}
