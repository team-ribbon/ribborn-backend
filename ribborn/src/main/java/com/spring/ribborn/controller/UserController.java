package com.spring.ribborn.controller;

import com.spring.ribborn.dto.requestDto.LoginRequestDto;
import com.spring.ribborn.dto.requestDto.UserRequestDto;
import com.spring.ribborn.dto.requestDto.UserUpdateRequestDto;
import com.spring.ribborn.dto.responseDto.UserResponseDto;
import com.spring.ribborn.dto.responseDto.UserTokenResponseDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.jwt.JwtTokenProvider;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
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
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패 : username 또는 password 를 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    // 유저 정보 조회(토큰)
    @GetMapping("/api/users/auth")
    public UserTokenResponseDto userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long id = userDetails.getUserId();
        if(!(id == null)) {
            return userService.userAuth(id);
        } else {
            throw new IllegalArgumentException("만료된 토큰입니다");
        }
    }

    // 유저 상세페이지
    @GetMapping("/api/users/userinfo/{id}")
    public UserResponseDto userinfo(@PathVariable("id") Long id,
                                    @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                    @RequestParam(name = "postCategory") String postCategory){
        return userService.userInfo(pageable,id,postCategory);
    }

    // 마이페이지
    @GetMapping("/api/users/mypage")
    public UserResponseDto userinfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                    @RequestParam(name = "postCategory") String postCategory){

        return userService.userInfo(pageable,userDetails.getUserId(),postCategory);
    }

    // 유저 정보 수정
    @PutMapping("/api/users/mypage")
    public ResponseEntity<ApiResponseMessage> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto , @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUserId();
        userService.updateUser(userUpdateRequestDto, userId);
        ApiResponseMessage message = new ApiResponseMessage("Success", "수정이 완료되었습니다", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
        }

}