package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.MainPageResponseDto;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<MainPageResponseDto.mainPage> getLookList(
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PageableDefault(size = 6) Pageable pageable) {
        ResponseEntity<MainPageResponseDto.mainPage> postList = mainPageService.getPostList(pageable, userDetails);
        return postList;
    }
        // 룩리스트가 엔티티 화 되어있는데 그것을 LookbookResponseDto.LookbookMain 이것으로 바꿔준다.
//        return lookList.map(LookbookResponseDto.LookbookMain::from);

}
