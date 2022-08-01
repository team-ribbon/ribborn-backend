package com.spring.ribborn.service;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.requestDto.PostChangeRequestDto;
import com.spring.ribborn.dto.requestDto.PostProcessChangeRequestDto;
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

    //리폼 게시글 수정
    @Transactional
    public void reformDetailChange(Long postId, ReformChangeRequestDto reformChangeRequestDto) {
        Post post = postRepository.findById(postId).orElse(null);
        int a =0;
        int before = post.getContents().size()-1;

        for(int i = 0; i < reformChangeRequestDto.getImageUrl().size(); i++){
            Contents content;
            if(i > before){
                content = new Contents();
            }else{
                content = post.getContents().get(i);
                String[] split = content.getImage().split("com/");
                awsS3Service.deleteFile(split[1]);
            }

            if(reformChangeRequestDto.getImageUrl().get(i).isEmpty()){
                content.setImage(reformChangeRequestDto.getFileUrl().get(a));
                content.setContent(reformChangeRequestDto.getContent());
                a += 1;
            }else{
                content.setImage(reformChangeRequestDto.getImageUrl().get(i));
                content.setContent(reformChangeRequestDto.getContent());
            }
            post.settingContents(content);
        }

        post.setTitle(reformChangeRequestDto.getTitle());
        post.setCategory(reformChangeRequestDto.getCategory());
        post.setRegion(reformChangeRequestDto.getRegion());
    }

    //나머지 게시글 수정
    @Transactional
    public void postDetailChange(Long postId, PostChangeRequestDto postChangeRequestDto){
        Post post = postRepository.findById(postId).orElse(null);
        int a =0;
        int before = post.getContents().size()-1;

        for(int i = 0; i < postChangeRequestDto.getImageUrl().size(); i++){
            Contents content;
            if(i > before){
                content = new Contents();
            }else{
                content = post.getContents().get(i);
                String[] split = content.getImage().split("com/");
                awsS3Service.deleteFile(split[1]);
            }

            if(postChangeRequestDto.getImageUrl().get(i).isEmpty()){
                content.setImage(postChangeRequestDto.getFileUrl().get(a));
                content.setContent(postChangeRequestDto.getContent());
                a += 1;
            }else{
                content.setImage(postChangeRequestDto.getImageUrl().get(i));
                content.setContent(postChangeRequestDto.getContent());
            }
            post.settingContents(content);
        }

        post.setTitle(postChangeRequestDto.getTitle());
        post.setCategory(postChangeRequestDto.getCategory());

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


    @Transactional
    public void postProcessingChange(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시긇이 존재하지 않습니다.")
        );
        if(post.getProcess().equals("before")){
            post.setProcess("ing");
        }
        else if(post.getProcess().equals("ing")){
            post.setProcess("before");
        }else {
            throw new IllegalArgumentException("다시한번 확인해주세요");
        }


    }

    @Transactional
    public void postProcessAfterChange(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시긇이 존재하지 않습니다.")
        );
        if(post.getProcess().equals("ing")){
            post.setProcess("after");
        }
        else if(post.getProcess().equals("after")){
            post.setProcess("ing");
        } else {
            throw new IllegalArgumentException("다시한번 확인해주세요");
        }


    }


    @Transactional
    public void postProcessChange(Long postId, PostProcessChangeRequestDto postProcessChangeRequestDto, Long userId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시긇이 존재하지 않습니다.")
        );
        if(postProcessChangeRequestDto.getProcess() != null){
            post.ProcessUpdate(postProcessChangeRequestDto);
        } else {
            throw new IllegalArgumentException("리폼 게시판에서만 바꿀수있습니다");
        }
    }




}
