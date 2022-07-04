package com.spring.ribborn.controller;


import com.spring.ribborn.dto.PostWriteRequestDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    @PostMapping("/api/qnaPosts")
    public ResponseEntity<ApiResponseMessage> qnaPostWrite(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFile,
                                                           @RequestPart(value = "key")PostWriteRequestDto postWriteRequestDto){


        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 작성 되었습니다.", "", "");
        return  new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }
}
