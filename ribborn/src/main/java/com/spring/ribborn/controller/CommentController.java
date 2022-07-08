package com.spring.ribborn.controller;

import com.spring.ribborn.dto.requestDto.CommentWriteRequestDto;
import com.spring.ribborn.dto.responseDto.CommentResponseDto;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //리뷰 작성
    @PostMapping("/api/post/{postId}/comment")
    public ResponseEntity<ApiResponseMessage> commentWrite(@PathVariable("postId") Long postId, @RequestBody CommentWriteRequestDto commentWriteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        commentService.commentWrite(postId,commentWriteRequestDto,userDetails.getUsername());

        ApiResponseMessage message = new ApiResponseMessage("Success", "댓글이 작성 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    //리뷰 조회(페이징)
    @GetMapping("/api/comments/{postId}")
    public Page<CommentResponseDto> commentResponse(@PathVariable("postId") Long postId,@PageableDefault(page = 0,size = 5) final Pageable pageable){
        return commentService.commendFind(postId, pageable);
    }

    //리뷰 수정
    @PutMapping("/api/post/{postId}/comment/{commentId}")
    public ResponseEntity<ApiResponseMessage> commentChange(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,@RequestBody CommentWriteRequestDto commentWriteRequestDto){

        commentService.commentChange(commentId, commentWriteRequestDto);

        ApiResponseMessage message = new ApiResponseMessage("Success", "댓글이 수정 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    //리뷰 삭제
    @DeleteMapping("/api/post/{postId}/comment/{commentId}")
    public ResponseEntity<ApiResponseMessage> commentDelete(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){

        commentService.commentDelete(commentId);
        ApiResponseMessage message = new ApiResponseMessage("Success", "댓글이 삭제 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }
}
