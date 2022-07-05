package com.spring.ribborn.controller;

import com.spring.ribborn.dto.requestDto.LoginRequestDto;
import com.spring.ribborn.dto.requestDto.UserRequestDto;
import com.spring.ribborn.dto.responseDto.UserResponseDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.jwt.JwtTokenProvider;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.UserRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/api/users/register/users")
    public ResponseEntity<ApiResponseMessage> registerUser(@RequestBody UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);

        ApiResponseMessage message = new ApiResponseMessage("Success", "회원가입이 완료되었습니다", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    // 아이디 중복체크
    @PostMapping("/api/users/register/idCheck")
    public ResponseEntity<ApiResponseMessage> idCheck(@RequestBody LoginRequestDto userRequestDto){
        userService.useridCheck(userRequestDto);

        ApiResponseMessage message = new ApiResponseMessage("Success", "사용가능한 아이디입니다", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/api/users/login")
    public ResponseEntity<String> login(final HttpServletResponse response, @RequestBody LoginRequestDto loginRequestDto) {
        if (userService.login(loginRequestDto)) {
            String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
            System.out.println("token = " + token);
            response.addHeader("Authorization", token);
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패 : username 또는 password 를 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    // 유저 상세페이지
    @GetMapping("/api/users/userinfo/{id}")
    public UserResponseDto userinfo(@PathVariable("id") Long id , @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        System.out.println("username = " + username);
        if(id.equals(userDetails.getUser().getId())){
            return userService.userInfo(id);
        } else {
        throw new IllegalArgumentException("로그인 해주세요");
        }
    }



}