package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.LookbookResponseDto;
import com.spring.ribborn.dto.responseDto.PostWriteResponseDto;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.PostQnaService;
import com.spring.ribborn.service.PostReviewService;
import com.spring.ribborn.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PostWriteController {
    private final PostQnaService postQnaService;
    private final PostReviewService postReviewService;

    // 질문 목록페이지 조회
    @GetMapping("/api/qnaList")
    public ResponseEntity<PostWriteResponseDto.WriteMain> getQnaList(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 6) Pageable pageable) {
        ResponseEntity<PostWriteResponseDto.WriteMain> lookList = postQnaService.getQna(pageable, userDetails);
        return lookList;
    }

    // 질문 상세페이지 조회
    @GetMapping("/api/qnaPosts/{postid}")
    public ResponseEntity<PostWriteResponseDto> getQnaPost(@PathVariable Long postId) {
        try{
            return new ResponseEntity(postQnaService.getDetail(postId), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 후기 목록페이지 조회
    @GetMapping("/api/reviewList")
    public ResponseEntity<PostWriteResponseDto.WriteMain> getReviewList(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 12) Pageable pageable) {
        ResponseEntity<PostWriteResponseDto.WriteMain> lookList = postReviewService.getReviews(pageable, userDetails);
        return lookList;
    }

    // 후기 상세페이지 조회
    @GetMapping("/api/reviewPosts/{postid}")
    public ResponseEntity<PostWriteResponseDto> getReviewPost(@PathVariable Long postId) {
        try{
            return new ResponseEntity(postReviewService.getDetail(postId), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
