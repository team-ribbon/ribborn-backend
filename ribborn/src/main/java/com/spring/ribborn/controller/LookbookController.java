package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.LookbookResponseDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.LookbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LookbookController {
    private final LookbookService lookbookService;

    // 룩북 목록페이지 조회
//    @GetMapping("/api/lookList")
//    public Page<LookbookResponseDto.LookbookMain> getLookbooks(
//            @PageableDefault(size = 6, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Post> lookList = lookbookService.getLookbooks(pageable);
//        return lookList.map(LookbookResponseDto.LookbookMain::from);
//    }

    @GetMapping("/api/lookList")
    public ResponseEntity<LookbookResponseDto.LookbookMain> getLookList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(size = 6, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        ResponseEntity<LookbookResponseDto.LookbookMain> lookList = lookbookService.getLookbooks(pageable);
        return lookList;
    }
}
