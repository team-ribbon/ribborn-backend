package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.LookbookResponseDto;
import com.spring.ribborn.dto.responseDto.ReformResponseDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.ReformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReformController {
    private final ReformService reformService;

    // 리폼견적 목록페이지 조회
    @GetMapping("/api/reformList")
    public Page<ReformResponseDto.ReformMain> getReformList(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 6) Pageable pageable) {
        Page<Post> reformList = reformService.getReforms(pageable);
        return reformList.map(ReformResponseDto.ReformMain::from);
    }

    // 리폼견적 상세페이지 조회
    @GetMapping("/api/reformPost/{postid}")
    public ResponseEntity<ReformResponseDto> getReformPost(@PathVariable Long postId) {
        try{
            return new ResponseEntity(reformService.getDetail(postId), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
