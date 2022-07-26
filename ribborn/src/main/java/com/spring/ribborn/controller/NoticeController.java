package com.spring.ribborn.controller;

import com.spring.ribborn.dto.requestDto.NoticeRequestDto;
import com.spring.ribborn.dto.responseDto.NoticeResponseDto;
import com.spring.ribborn.model.Notice;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/api/notice")
    public Page<NoticeResponseDto> allNotice(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Notice> noticeList = noticeService.allNotice(pageable);
        return noticeList.map(NoticeResponseDto::from);
    }

    @GetMapping("/api/notice/{noticeId}")
    public NoticeResponseDto getNotice(@PathVariable Long noticeId) {
        Notice notice = noticeService.getNotice(noticeId).orElseThrow(
                () -> new IllegalArgumentException("공지가 존재하지 않습니다.")
        );
        return NoticeResponseDto.from(notice);
    }

    @PostMapping("/api/notice")
    public ResponseEntity<Boolean> createNotice(@ModelAttribute NoticeRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        noticeService.createNotice(requestDto,userDetails);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/api/notice/{noticeId}")
    public ResponseEntity<Boolean> editNotice(@PathVariable Long noticeId, @ModelAttribute NoticeRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        noticeService.editNotice(requestDto,userDetails,noticeId);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/api/notice/{noticeId}")
    public ResponseEntity<Boolean> deleteNotice(@PathVariable Long noticeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeService.deleteNotice(userDetails,noticeId);
        return ResponseEntity.ok(true);
    }
}
