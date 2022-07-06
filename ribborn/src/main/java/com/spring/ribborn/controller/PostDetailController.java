package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.service.PostDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostDetailController {
    private final PostDetailService postDetailService;


    @GetMapping("/api/qnaList/{postId}")
    public PostDetailResponseDto postDetailView(@PathVariable("postId") Long postId, @RequestParam int page, @RequestParam int size){

        return postDetailService.postDetailView(postId, page, size);
    }
}
