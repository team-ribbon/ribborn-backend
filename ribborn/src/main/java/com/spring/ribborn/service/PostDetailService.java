package com.spring.ribborn.service;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.responseDto.*;
import com.spring.ribborn.model.Love;
import com.spring.ribborn.repository.LoveFindRepository;
import com.spring.ribborn.repository.PostDetailRepository;
import com.spring.ribborn.security.UserDetailsImpl;
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

    private final LoveFindRepository loveFindRepository;

    @Transactional
    public PostDetailResponseMsg postDetailView(Long postId, Pageable pageable, UserDetailsImpl userDetails) {
        PostDetailResponseDto postDetail = postDetailRepository.findPostDetail(postId);

        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        Page<CommentResponseDto> commentResponseDtos = commentService.commendFind(postId, pageable);

        if(userDetails != null){
            Love byPostIdAndUserId = loveFindRepository.findByPostIdAndUserId(postId, userDetails.getUserId());

            if(byPostIdAndUserId != null){
                postDetail.setLiked(true);
            }else{
                postDetail.setLiked(false);
            }
        }


        PostDetailResponseMsg msg = new PostDetailResponseMsg();

        postDetail.contentSetting(contents);
        postDetail.setTotalPage(commentResponseDtos.getTotalPages());
        postDetail.setPageNumber(commentResponseDtos.getPageable().getPageNumber());
        postDetail.setCommentCount(commentResponseDtos.getTotalElements());

        msg.setComment(commentResponseDtos.getContent());
        msg.setPost(postDetail);
        return msg;
    }


    //리폼 상세페이지 조회 서비스
    public ReformPostDetailResponseDto reformPostDetailView(Long postId) {
        ReformPostDetailResponseDto postDetail = postDetailRepository.reformPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        postDetail.contentSetting(contents);
        return postDetail;
    }

    //룩북 상세페이지 조회 서비스
    public LookBookDetailResponseDto lookBookPostDetailView(Long postId, UserDetailsImpl userDetails) {
        LookBookDetailResponseDto postDetail = postDetailRepository.lookBookPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        postDetail.contentSetting(contents);

        if(userDetails != null){
            Love byPostIdAndUserId = loveFindRepository.findByPostIdAndUserId(postId, userDetails.getUserId());
            if(byPostIdAndUserId != null){
                postDetail.setLiked(true);
            }else{
                postDetail.setLiked(false);
            }
        }

        return postDetail;
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        postDetailRepository.deletePost(postId);
    }
}
