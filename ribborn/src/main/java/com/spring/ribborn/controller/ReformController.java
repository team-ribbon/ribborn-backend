package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.ReformResponseDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.ReformService;
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
public class ReformController {
    private final ReformService reformService;

    // 리폼견적 목록페이지 조회
//    @GetMapping("/api/reformList")
//    public Page<ReformResponseDto.Reform> getReformList(
//            @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 6) Pageable pageable) {
//        Page<Post> reformList = reformService.getReforms(pageable);
//        return reformList.map(ReformResponseDto.Reform::from);
//    }

    @GetMapping("/api/reformList")
    public ResponseEntity<ReformResponseDto.Reform> getReformList(
            Pageable pageable,
            @RequestParam(name = "category") String category) {
        ResponseEntity<ReformResponseDto.Reform> lookList = reformService.getReforms(pageable,category);
        return lookList;
    }
}
