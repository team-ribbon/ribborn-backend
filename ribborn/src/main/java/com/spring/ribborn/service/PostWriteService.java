package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.dto.responseDto.PostWriteResponseDto;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.ContentsRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostWriteService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ContentsRepository contentsRepository;

    //게시글 작성
    @Transactional
    public void postWrite(PostWriteRequestDto postWriteRequestDto) {
        User user = userRepository.findByUsername(postWriteRequestDto.getUsername()).orElse(null);
        Post post = Post.createNormalPost(
                user,
                postWriteRequestDto.getRegion(),
                postWriteRequestDto.getProcess(),
                postWriteRequestDto.getPostCategory(),
                postWriteRequestDto.getTitle(),
                postWriteRequestDto.getCategory()
        );

        if(postWriteRequestDto.getImages().isEmpty()){
            Contents contents = Contents.noImageContent(postWriteRequestDto.getContent());
            post.settingContents(contents);
        }else{
            for(String image : postWriteRequestDto.getImages()){
                Contents contents = Contents.imageAndContent(image,postWriteRequestDto.getContent());
                post.settingContents(contents);
            }
        }

    }

    //룩북 작성
    @Transactional
    public void lookBookPostWrite(LookBookPostWriteDto lookBookPostWriteDto) {
        User user = userRepository.findByUsername(lookBookPostWriteDto.getUsername()).orElse(null);
        user.settingIntroduction(lookBookPostWriteDto.getIntroduction());
        Post post = Post.createLookBookPost(
                user,
                lookBookPostWriteDto.getPostCategory(),
                lookBookPostWriteDto.getCategory(),
                lookBookPostWriteDto.getIntroduction()
        );
        for(int i = 0; i < lookBookPostWriteDto.getImages().size(); i++){
            Contents contents = Contents.imageAndContent(lookBookPostWriteDto.getImages().get(i), lookBookPostWriteDto.getContent());
            post.settingContents(contents);
        }
        postRepository.save(post);
    }

//---------------------------------------------------------------------------------------------------------------

    // 후기 & 질문 게시판 조회
//    @Transactional
//    public Page<Post> getWrite(Pageable pageable) {
//        return postRepository.findAll(pageable);
//    }

    public ResponseEntity<PostWriteResponseDto.WritePost> getQna(Pageable pageable, String category) {
        List<Post> posts;
        if(category.equals("all")){
            posts = postRepository.findAllByPostCate("qna",pageable);
        }else{
            posts = postRepository.findAllByPostCateAndCategory("qna",pageable,category);
        }

        List<PostWriteResponseDto.WritePost> qnaList = new ArrayList<>();

        for (Post post : posts) {
            /**
             * 반복문을 돌면서 쿼리가 지속 발생하는 문제 해결
             * 어차피 image와 content는 0번만 필요하기 때문에, 프록시 상태의 Contents를 get 하면서 쿼리를 발생시킨 후,
             * 이후 영속성 컨텍스트에 올라가 있는 Contents를 이용한다.
             */
            /*Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());*/
            PostWriteResponseDto.WritePost mainDto = PostWriteResponseDto.WritePost.builder()
                    .id(post.getId())
                    .image(post.getContents().get(0).getImage())
                    .content(post.getContents().get(0).getContent())
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getCommentCount())
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .build();
            qnaList.add(mainDto);
        }
        return new ResponseEntity(qnaList, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostWriteResponseDto.WritePost> getReview(Pageable pageable,String category) {
        List<Post> posts;

        if(category.equals("all")){
            posts = postRepository.findAllByPostCate("review", pageable);
        }else{
            posts = postRepository.findAllByPostCateAndCategory("review", pageable,category);
        }
        List<PostWriteResponseDto.WritePost> reviewList = new ArrayList<>();

        for (Post post : posts) {
            /**
             * 반복문을 돌면서 쿼리가 지속 발생하는 문제 해결
             * 어차피 image와 content는 0번만 필요하기 때문에, 프록시 상태의 Contents를 get 하면서 쿼리를 발생시킨 후,
             * 이후 영속성 컨텍스트에 올라가 있는 Contents를 이용한다.
             */
            /*Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());*/
            PostWriteResponseDto.WritePost mainDto = PostWriteResponseDto.WritePost.builder()
                    .id(post.getId())
                    .image(post.getContents().get(0).getImage())
                    .content(post.getContents().get(0).getContent())
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getCommentCount())
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .build();
            reviewList.add(mainDto);
        }
        return new ResponseEntity(reviewList, HttpStatus.OK);
    }
}
