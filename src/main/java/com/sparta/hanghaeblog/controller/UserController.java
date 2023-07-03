package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ResponseEntity("회원가입 성공a~!", HttpStatusCode.valueOf(202));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse res){
        userService.login(requestDto, res);
        return new ResponseEntity("로그인이 성공a~!", HttpStatusCode.valueOf(202));
    }
    //argument resolver
}
