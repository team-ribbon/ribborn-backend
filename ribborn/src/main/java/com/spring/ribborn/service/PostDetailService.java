package com.spring.ribborn.service;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.responseDto.CommentResponseDto;
import com.spring.ribborn.dto.responseDto.LookBookDetailResponseDto;
import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.dto.responseDto.ReformPostDetailResponseDto;
import com.spring.ribborn.repository.PostDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostDetailService {

    private final PostDetailRepository postDetailRepository;
    private final CommentService commentService;

    @Transactional
    public PostDetailResponseDto postDetailView(Long postId, Pageable pageable) {
        PostDetailResponseDto postDetail = postDetailRepository.findPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        Page<CommentResponseDto> commentResponseDtos = commentService.commendFind(postId, pageable);

        postDetail.contentSetting(contents);
        postDetail.setComment(commentResponseDtos.getContent());
        postDetail.setTotalPage(commentResponseDtos.getTotalPages());
        postDetail.setPageNumber(commentResponseDtos.getPageable().getPageNumber());

        return postDetail;
    }


    //리폼 상세페이지 조회 서비스
    public ReformPostDetailResponseDto reformPostDetailView(Long postId) {
        ReformPostDetailResponseDto postDetail = postDetailRepository.reformPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        postDetail.contentSetting(contents);
        return postDetail;
    }

    //룩북 상세페이지 조회 서비스
    public LookBookDetailResponseDto lookBookPostDetailView(Long postId) {
        LookBookDetailResponseDto postDetail = postDetailRepository.lookBookPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        postDetail.contentSetting(contents);
        return postDetail;
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        postDetailRepository.deletePost(postId);
    }
}
