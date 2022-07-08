package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.IntroductionDto;
import com.spring.ribborn.dto.responseDto.LookBookDetailResponseDto;
import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.dto.responseDto.ReformPostDetailResponseDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.service.CommentService;
import com.spring.ribborn.service.PostDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostDetailController {
    private final PostDetailService postDetailService;


    //질문게시판, 후기게시판 상세조회

    @GetMapping(value = {"/api/qnaList/{postId}", "/api/reviewPosts/{postId}"})
    public PostDetailResponseDto postDetailView(@PathVariable("postId") Long postId,@PageableDefault(page = 0,size = 5) final Pageable pageable){
        return postDetailService.postDetailView(postId, pageable);
    }

    //리폼 견적 게시판 조회
    @GetMapping("/api/reformPosts/{postId}")
    public ReformPostDetailResponseDto reformPostDetailView(@PathVariable("postId") Long postId){
        return postDetailService.reformPostDetailView(postId);
    }

    //룩북 게시판 조회
    @GetMapping("/api/lookPosts/{postId}")
    public LookBookDetailResponseDto lookBookPostDetailView(@PathVariable("postId") Long postId){
        return postDetailService.lookBookPostDetailView(postId);
    }

    //룩북 게시판 접근시
    /*@GetMapping("/api/lookPosts")
    public IntroductionDto introduction(@AuthenticationPrincipal){

    }*/
    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<ApiResponseMessage> postDelete(@PathVariable("postId") Long postId){

        postDetailService.deletePost(postId);

        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 삭제 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }
}
