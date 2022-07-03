package com.spring.ribborn.controller;

import com.spring.ribborn.dto.UserRequestDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 회원가입
    @PostMapping("/api/users/register/users")
    public ResponseEntity<ApiResponseMessage> registerUser(@RequestBody UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);

        ApiResponseMessage message = new ApiResponseMessage("Success", "회원가입이 완료되었습니다", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }
}
