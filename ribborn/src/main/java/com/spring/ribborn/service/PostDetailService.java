package com.spring.ribborn.service;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.requestDto.PostChangeRequestDto;
import com.spring.ribborn.dto.requestDto.ReformChangeRequestDto;
import com.spring.ribborn.dto.responseDto.*;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Love;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.LoveFindRepository;
import com.spring.ribborn.repository.PostDetailRepository;
import com.spring.ribborn.repository.PostRepository;
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
    private final AwsS3Service awsS3Service;
    private final CommentService commentService;
    private final PostRepository postRepository;

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

    //?????? ??????????????? ?????? ?????????
    public ReformPostDetailResponseDto reformPostDetailView(Long postId) {
        ReformPostDetailResponseDto postDetail = postDetailRepository.reformPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        postDetail.contentSetting(contents);
        return postDetail;
    }

    //?????? ??????????????? ?????? ?????????
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
    //?????? ????????? ??????
    @Transactional
    public void reformDetailChange(Long postId, ReformChangeRequestDto reformChangeRequestDto) {
        Post post = postRepository.findById(postId).orElse(null);
        int a =0;
        int before = post.getContents().size()-1;

        for(int i = 0; i < reformChangeRequestDto.getImageUrl().size(); i++){
            Contents content;
            if(i > before){
                content = Contents.emptyContent();
            }else{
                content = post.getContents().get(i);
                String[] split = content.getImage().split("com/");
                awsS3Service.deleteFile(split[1]);
            }

            if(reformChangeRequestDto.getImageUrl().get(i).isEmpty()){
                content.contentsChange(reformChangeRequestDto.getFileUrl().get(a), reformChangeRequestDto.getContent());
                a += 1;
            }else{
                content.contentsChange(reformChangeRequestDto.getImageUrl().get(i), reformChangeRequestDto.getContent());
            }
            post.settingContents(content);
        }
        post.reformPostChange(reformChangeRequestDto.getTitle(), reformChangeRequestDto.getCategory(), reformChangeRequestDto.getRegion());
    }

    //????????? ????????? ??????
    @Transactional
    public void postDetailChange(Long postId, PostChangeRequestDto postChangeRequestDto){
        Post post = postRepository.findById(postId).orElse(null);
        int a =0;
        int before = post.getContents().size()-1;

        for(int i = 0; i < postChangeRequestDto.getImageUrl().size(); i++){
            Contents content;
            if(i > before){
                content = Contents.emptyContent();
            }else{
                content = post.getContents().get(i);
                String[] split = content.getImage().split("com/");
                awsS3Service.deleteFile(split[1]);
            }

            if(postChangeRequestDto.getImageUrl().get(i).isEmpty()){
                content.contentsChange(postChangeRequestDto.getFileUrl().get(a), postChangeRequestDto.getContent());
                a += 1;
            }else{
                content.contentsChange(postChangeRequestDto.getImageUrl().get(i), postChangeRequestDto.getContent());
            }
            post.settingContents(content);
        }
        post.normalPostChange(postChangeRequestDto.getTitle(), postChangeRequestDto.getCategory());
    }
    //????????? ??????
    @Transactional
    public void deletePost(Long postId) {
        postDetailRepository.deletePost(postId);
    }


}
