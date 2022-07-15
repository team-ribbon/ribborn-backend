package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.MainPageResponseDto;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class MainPageController {
    private final MainPageService mainPageService;

    @GetMapping("/api/home")
    public MainPageResponseDto getLookList() {
        return mainPageService.getPostList();
    }



}
