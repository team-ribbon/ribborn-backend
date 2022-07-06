package com.spring.ribborn.controller;


import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.dto.responseDto.LookbookResponseDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.model.Images;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.AwsS3Service;
import com.spring.ribborn.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final AwsS3Service awsS3Service;

    private final PostWriteService postWriteService;


    //질문,후기,리폼견적 게시판 작성
    @PostMapping(value = {"/api/qnaPosts","/api/reviewPosts","/api/reform-Posts"})
    public ResponseEntity<ApiResponseMessage> qnaPostWrite(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFile,
                                                           @RequestPart(value = "key")PostWriteRequestDto postWriteRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        List<String> strings = awsS3Service.uploadFile(multipartFile);
        postWriteRequestDto.setImages(strings);
        postWriteRequestDto.setUsername(userDetails.getUsername());
        postWriteService.postWrite(postWriteRequestDto);

        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 작성 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    //룩북 게시판 작성
    @PostMapping("/api/lookPosts")
    public ResponseEntity<ApiResponseMessage> lookPostWrite(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFile,
                                                            @RequestPart(value = "key") LookBookPostWriteDto lookBookPostWriteDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<String> strings = awsS3Service.uploadFile(multipartFile);

        lookBookPostWriteDto.setImages(strings);
        lookBookPostWriteDto.setUsername(userDetails.getUsername());
        postWriteService.lookBookPostWrite(lookBookPostWriteDto);


        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 작성 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

}
