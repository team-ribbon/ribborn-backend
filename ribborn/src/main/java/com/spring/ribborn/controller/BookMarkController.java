package com.spring.ribborn.controller;

import com.spring.ribborn.dto.requestDto.BookMarkRequestDto;
import com.spring.ribborn.dto.requestDto.LoveRequestDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @PostMapping("/api/post/{postId}/bookmark")
    public ResponseEntity<ApiResponseMessage> loveClick(@PathVariable("postId") Long postId,
                                                        @RequestBody BookMarkRequestDto bookMarkRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){

        bookMarkService.bookMarkClick(postId,bookMarkRequestDto,userDetails);

        ApiResponseMessage message = new ApiResponseMessage("Success", "", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

}
