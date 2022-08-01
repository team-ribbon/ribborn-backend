package com.spring.ribborn.service;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.requestDto.PostChangeRequestDto;
import com.spring.ribborn.dto.requestDto.ReformChangeRequestDto;
import com.spring.ribborn.dto.responseDto.*;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Love;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.*;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostDetailService {

    private final PostDetailRepository postDetailRepository;
    private final AwsS3Service awsS3Service;
    private final CommentService commentService;
    private final ContentsDeleteRepository contentsDeleteRepository;
    private final PostRepository postRepository;

    private final LoveFindRepository loveFindRepository;
    private final BookMarkFindRepository bookMarkFindRepository;
    private final ContentsRepository contentsRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public PostDetailResponseMsg postDetailView(Long postId, Pageable pageable, UserDetailsImpl userDetails) {

        PostDetailResponseDto postDetail = postDetailRepository.findPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        Page<CommentResponseDto> commentResponseDtos = commentService.commendFind(postId, pageable);

        if(userDetails != null){
            Love findLove = loveFindRepository.findByPostIdAndUserId(postId, userDetails.getUserId());
            Love findBookMark = bookMarkFindRepository.findByPostIdAndUserId(postId, userDetails.getUserId());

            postDetail.setLiked(findLove != null);
            postDetail.setBooked(findBookMark != null);
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
    @Transactional(readOnly = true)
    public ReformPostDetailResponseDto reformPostDetailView(Long postId, UserDetailsImpl userDetails) {
        ReformPostDetailResponseDto postDetail = postDetailRepository.reformPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        if(userDetails != null){
            Love findBookMark = bookMarkFindRepository.findByPostIdAndUserId(postId, userDetails.getUserId());
            postDetail.setBooked(findBookMark != null);
        }
        postDetail.contentSetting(contents);
        return postDetail;
    }

    //룩북 상세페이지 조회 서비스
    @Transactional(readOnly = true)

    public LookBookDetailResponseDto lookBookPostDetailView(Long postId, UserDetailsImpl userDetails) {
        LookBookDetailResponseDto postDetail = postDetailRepository.lookBookPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);
        postDetail.contentSetting(contents);

        if(userDetails != null){
            Love findLove = loveFindRepository.findByPostIdAndUserId(postId, userDetails.getUserId());
            Love findBookMark = bookMarkFindRepository.findByPostIdAndUserId(postId, userDetails.getUserId());

            postDetail.setLiked(findLove != null);
            postDetail.setBooked(findBookMark != null);
        }

        return postDetail;
    }
    //리폼 게시글 수정
    @Transactional
    public void reformDetailChange(Long postId, ReformChangeRequestDto reformChangeRequestDto) {
        Post post = postRepository.findById(postId).orElse(null);
        int a =0;
        int before = post.getContents().size()-1;

        if(reformChangeRequestDto.getImageUrl() != null){
            for(int i = 0; i < reformChangeRequestDto.getImageUrl().size(); i++){
                Contents content;
                if(i > before){
                    content = Contents.emptyContent();
                }else{
                    content = post.getContents().get(i);
                    if(reformChangeRequestDto.getDeleteImage() == null){
                        String[] split = content.getImage().split("com/");
                        awsS3Service.deleteFile(split[1]);
                    }
                }

                if(reformChangeRequestDto.getImageUrl().get(i).isEmpty()){
                    content.contentsChange(reformChangeRequestDto.getFileUrl().get(a), reformChangeRequestDto.getContent());
                    a += 1;
                }else{
                    content.contentsChange(reformChangeRequestDto.getImageUrl().get(i), reformChangeRequestDto.getContent());
                }
                post.settingContents(content);
            }
        }

        if(reformChangeRequestDto.getDeleteImage() != null){
            for(String image: reformChangeRequestDto.getDeleteImage()){
                Contents byImage = contentsRepository.findByImage(image).get(0);
                contentsDeleteRepository.deleteContents(byImage.getId());
                String[] split = image.split("com/");
                awsS3Service.deleteFile(split[1]);
            }
        }

        if(reformChangeRequestDto.getImageUrl() == null){
            Contents contents = Contents.emptyContent();
            contents.setContent(reformChangeRequestDto.getContent());
            post.settingContents(contents);
        }
        post.reformPostChange(reformChangeRequestDto.getTitle(), reformChangeRequestDto.getCategory(), reformChangeRequestDto.getRegion());
    }

    //나머지 게시글 수정
    @Transactional
    public void postDetailChange(Long postId, PostChangeRequestDto postChangeRequestDto){
        Post post = postRepository.findById(postId).orElse(null);
        int a =0;
        int before = post.getContents().size()-1;
        if(postChangeRequestDto.getImageUrl() != null){
            for(int i = 0; i < postChangeRequestDto.getImageUrl().size(); i++){
                Contents content;
                if(i > before){
                    content = Contents.emptyContent();
                }else{
                    content = post.getContents().get(i);
                    if(postChangeRequestDto.getDeleteImage() == null){
                        String[] split = content.getImage().split("com/");
                        awsS3Service.deleteFile(split[1]);
                    }
                }

                if(postChangeRequestDto.getImageUrl().get(i).isEmpty()){
                    content.contentsChange(postChangeRequestDto.getFileUrl().get(a), postChangeRequestDto.getContent());
                    a += 1;
                }else{
                    content.contentsChange(postChangeRequestDto.getImageUrl().get(i), postChangeRequestDto.getContent());
                }
                post.settingContents(content);
            }
        }

        if(postChangeRequestDto.getDeleteImage() != null){
            for(String image: postChangeRequestDto.getDeleteImage()){
                Contents byImage = contentsRepository.findByImage(image).get(0);
                contentsDeleteRepository.deleteContents(byImage.getId());
                String[] split = image.split("com/");
                awsS3Service.deleteFile(split[1]);
            }
        }

        if(postChangeRequestDto.getImageUrl() == null){
            Contents contents = Contents.emptyContent();
            contents.setContent(postChangeRequestDto.getContent());
            post.settingContents(contents);
        }
        post.normalPostChange(postChangeRequestDto.getTitle(), postChangeRequestDto.getCategory());
    }
    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        for(Contents con : post.getContents()){
            if(con.getImage() != null){
                String[] split = con.getImage().split("com/");
                awsS3Service.deleteFile(split[1]);
            }
        }
        commentRepository.deleteAllByPost(post);
        loveFindRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }


}
