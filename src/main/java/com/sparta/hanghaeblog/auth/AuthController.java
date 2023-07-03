package com.sparta.hanghaeblog.auth;

import com.sparta.hanghaeblog.entity.UserRoleEnum;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Claims;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization"; //헤더부분
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }




    @GetMapping("/create-cookie")
    public String createCookie(HttpServletResponse res) { //쿠키를 만드는 코드  //HttpServletResponse 서블릿에서 만들어준 리스폰스객체에 데이터를 담으면 클라이언트로 자연스럽게 반환이됨
        addCookie("Robbie Auth", res);

        return "createCookie";
    }

    @GetMapping("/get-cookie") //가져오는 방법
    public String getCookie(@CookieValue(AUTHORIZATION_HEADER) String value) {
        // AUTHORIZATION_HEADER라는 상수를 괄호 안에 넣고 변수는 value로 받는다.
        // HttpServletRequest에 들어있는 쿠키중에서 Authorization라는 이름으로 된 쿠키를 @CookieValue어노테이션을 통해서 가지고 온다. 그럼 해당하는 변수의 값이 value에 들어감
        System.out.println("value = " + value);

        return "getCookie : " + value;
    }




    @GetMapping("/create-session")
    public String createSession(HttpServletRequest req) {
        // 세션이 존재할 경우 세션 반환, 없을 경우 새로운 세션을 생성한 후 반환
        HttpSession session = req.getSession(true);

        // 세션에 저장될 정보 Name - Value 를 추가합니다.
        session.setAttribute(AUTHORIZATION_HEADER, "Robbie Auth");

        return "createSession";
    }

    @GetMapping("/get-session")
    public String getSession(HttpServletRequest req) {
        // 세션이 존재할 경우 세션 반환, 없을 경우 null 반환
        HttpSession session = req.getSession(false);

        String value = (String) session.getAttribute(AUTHORIZATION_HEADER); // 가져온 세션에 저장된 Value 를 Name 을 사용하여 가져옵니다.
        System.out.println("value = " + value);

        return "getSession : " + value;
    }






    @GetMapping("/create-jwt")
    public String createJwt(HttpServletResponse res) {
        String token = jwtUtil.createToken("Robbie"); // Jwt 생성
        jwtUtil.addJwtToCookie(token, res); // Jwt 쿠키 저장
        return "createJwt : " + token;
    }

    @GetMapping("/get-jwt")
    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue); // JWT 토큰 substring
        if(!jwtUtil.validateToken(token)){ // 토큰 검증
            throw new IllegalArgumentException("Token Error");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token); // 토큰에서 사용자 정보 가져오기
        String username = info.getSubject(); // 사용자 username
        System.out.println("username = " + username);
        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY); // 사용자 권한
        System.out.println("authority = " + authority);
        return "getJwt : " + username + ", " + authority;
    }





    public static void addCookie(String cookieValue, HttpServletResponse res) { //쿠키를 저장하는 메서드
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            //URLEncoder이라는 클래스를 이용해서 encode라는 메서드를 사용해서 replaceAll로 공백을 바꿔주기

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue); // Name-Value //스프링에서 Cookie를 만들 수 있게끔 Cookie클래스를 제공함
            cookie.setPath("/"); //  패스넣어주기
            cookie.setMaxAge(30 * 60); // 만료기한

            // Response 객체에 Cookie 추가
            res.addCookie(cookie); //addCookies라는 메서드가 이미 존재한다. 쿠키를 담을 공간이 이미 마련되어있다.
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}