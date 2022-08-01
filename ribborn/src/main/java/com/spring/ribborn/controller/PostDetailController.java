package com.spring.ribborn.controller;

import com.spring.ribborn.dto.requestDto.PostChangeRequestDto;
import com.spring.ribborn.dto.requestDto.PostProcessChangeRequestDto;
import com.spring.ribborn.dto.requestDto.ReformChangeRequestDto;
import com.spring.ribborn.dto.responseDto.*;
import com.spring.ribborn.exception.ApiResponseMessage;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.AwsS3Service;
import com.spring.ribborn.service.PostDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostDetailController {
    private final PostDetailService postDetailService;
    private final AwsS3Service awsS3Service;

    //질문게시판, 후기게시판 상세조회
    @GetMapping(value = {"/api/qnaPosts/{postId}", "/api/reviewPosts/{postId}"})
    public PostDetailResponseMsg postDetailView(@PathVariable("postId") Long postId, @PageableDefault(page = 0,size = 5, sort = "id", direction = Sort.Direction.DESC) final Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postDetailService.postDetailView(postId, pageable, userDetails);
    }

    //리폼 견적 게시판 조회
    @GetMapping("/api/reformPosts/{postId}")
    public ReformPostDetailResponseDto reformPostDetailView(@PathVariable("postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postDetailService.reformPostDetailView(postId, userDetails);
    }

    //룩북 게시판 조회
    @GetMapping("/api/lookPosts/{postId}")
    public LookBookDetailResponseDto lookBookPostDetailView(@PathVariable("postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postDetailService.lookBookPostDetailView(postId, userDetails);
    }

    //룩북 게시판 접근시
    @GetMapping("/api/lookPosts")
    public IntroductionDto introduction(@AuthenticationPrincipal UserDetailsImpl userDetails){
        IntroductionDto introductionDto = new IntroductionDto();
        introductionDto.setIntroduction(userDetails.getIntroduction());
        return introductionDto;
    }

    //게시글 수정
    @PutMapping(value = {"/api/qnaPosts/{postId}","/api/reviewPosts/{postId}","/api/lookPosts/{postId}"})
    public ResponseEntity<ApiResponseMessage> postChange(@PathVariable("postId") Long postId,
                                                         @RequestPart(value = "file", required = false) List<MultipartFile> fileList,
                                                         @RequestPart(value = "key") PostChangeRequestDto postChangeRequestDto){

        List<String> strings = null;

        if(fileList != null){
            strings = awsS3Service.uploadFile(fileList);
        }
        postChangeRequestDto.setFileUrl(strings);
        postDetailService.postDetailChange(postId,postChangeRequestDto);
        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 수정 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    //리폼 게시글 수정
    @PutMapping("/api/reformPosts/{postId}")
    public ResponseEntity<ApiResponseMessage> reformPostChange(@PathVariable("postId") Long postId,
                                                               @RequestPart(value = "file", required = false) List<MultipartFile> fileList,
                                                               @RequestPart(value = "key")ReformChangeRequestDto reformChangeRequestDto){

        List<String> strings = null;

        if(fileList != null){
            strings = awsS3Service.uploadFile(fileList);
        }
        reformChangeRequestDto.setFileUrl(strings);
        postDetailService.reformDetailChange(postId, reformChangeRequestDto);
        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 수정 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<ApiResponseMessage> postDelete(@PathVariable("postId") Long postId){

        postDetailService.deletePost(postId);

        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글이 삭제 되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }


    // 진행(process) 상태 변경  진행전 -> 진행중
    @PutMapping("/api/processIng/{postId}")
    public ResponseEntity<ApiResponseMessage> processBeforeChange(@PathVariable("postId") Long postId ,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails
                                                                 ){
        Long userId = userDetails.getUserId();
        postDetailService.postProcessingChange(postId, userId);
        ApiResponseMessage message = new ApiResponseMessage("Success", "상태가 변경되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }


    // 진행 상태 변경 진행중 -> 완료
    @PutMapping("/api/processAfter/{postId}")
    public ResponseEntity<ApiResponseMessage> processingChange(@PathVariable("postId") Long postId ,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUserId();
        postDetailService.postProcessAfterChange(postId, userId);
        ApiResponseMessage message = new ApiResponseMessage("Success", "상태가 변경되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }



    @PutMapping("/api/post/{postId}/process")
    public ResponseEntity<ApiResponseMessage> processChange(@PathVariable("postId") Long postId ,
                                                               @RequestBody PostProcessChangeRequestDto postProcessChangeRequestDto,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        postDetailService.postProcessChange(postId, postProcessChangeRequestDto ,userId);
        ApiResponseMessage message = new ApiResponseMessage("Success", "상태가 변경되었습니다.", "", "");
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);

    }


}
