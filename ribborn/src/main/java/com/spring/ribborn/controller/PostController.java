package com.spring.ribborn.controller;


import com.spring.ribborn.dto.PostWriteRequestDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.AwsS3Service;
import com.spring.ribborn.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final AwsS3Service awsS3Service;

    private final PostWriteService postWriteService;


    //질문 게시판 작성
    @PostMapping("/api/qnaPosts")
    public ResponseEntity<ApiResponseMessage> qnaPostWrite(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFile,
                                                           @RequestPart(value = "key")PostWriteRequestDto postWriteRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<String> strings = awsS3Service.uploadFile(multipartFile);


        postWriteRequestDto.setImages(strings);
        postWriteRequestDto.setNickname(userDetails.getNickname());
        postWriteRequestDto.setUsername(userDetails.getUsername());


        postWriteService.postWrite(postWriteRequestDto);

        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 작성 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }
}
