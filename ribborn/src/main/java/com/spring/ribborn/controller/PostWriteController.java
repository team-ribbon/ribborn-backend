package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.PostWriteResponseDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostWriteController {
    private final PostWriteService postWriteService;

    // 질문 목록페이지 조회
//    @GetMapping("/api/qnaList")
//    public Page<PostWriteResponseDto.WritePost> getQnaList(
//            @PageableDefault(size = 6, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Post> qnaList = postWriteService.getWrite(pageable);
//        return qnaList.map(PostWriteResponseDto.WritePost::from);
//    }

    @GetMapping("/api/qnaList")
    public ResponseEntity<PostWriteResponseDto.WritePost> getQnaList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Pageable pageable,
            @RequestParam(name = "category") String category) {
        ResponseEntity<PostWriteResponseDto.WritePost> qnaList = postWriteService.getQna(pageable,category);
        return qnaList;
    }

    // 후기 목록페이지 조회
//    @GetMapping("/api/reviewList")
//    public Page<PostWriteResponseDto.WritePost> getReviewList(
//            @PageableDefault(size = 12, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Post> reviewList = postWriteService.getWrite(pageable);
//        return reviewList.map(PostWriteResponseDto.WritePost::from);
//    }

    @GetMapping("/api/reviewList")
    public ResponseEntity<PostWriteResponseDto.WritePost> getReviewList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Pageable pageable,
            @RequestParam(name = "category") String category) {
        ResponseEntity<PostWriteResponseDto.WritePost> reviewList = postWriteService.getReview(pageable,category);
        return reviewList;
    }
}
