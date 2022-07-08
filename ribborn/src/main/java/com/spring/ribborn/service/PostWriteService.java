package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.dto.responseDto.PostWriteResponseDto;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.ContentsRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.repository.PostWriteRepository;
import com.spring.ribborn.repository.UserRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostWriteService {
    private final PostWriteRepository postWriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ContentsRepository contentsRepository;

    //게시글 작성
    @Transactional
    public void postWrite(PostWriteRequestDto postWriteRequestDto) {
        User user = userRepository.findByUsername(postWriteRequestDto.getUsername()).orElse(null);
        postWriteRepository.postWrite(postWriteRequestDto,user);
    }

    //룩북 작성
    @Transactional
    public void lookBookPostWrite(LookBookPostWriteDto lookBookPostWriteDto) {
        User user = userRepository.findByUsername(lookBookPostWriteDto.getUsername()).orElse(null);
        postWriteRepository.lookBookPostWrite(lookBookPostWriteDto,user);
    }

//---------------------------------------------------------------------------------------------------------------

    // 후기 & 질문 게시판 조회
//    @Transactional
//    public Page<Post> getWrite(Pageable pageable) {
//        return postRepository.findAll(pageable);
//    }

    @Transactional
    public ResponseEntity<PostWriteResponseDto.WriteMain> getQna(Pageable pageable) {
        List<Post> posts = postRepository.findAllByPostCate("질문게시판", pageable);
        List<PostWriteResponseDto.WriteMain> qnaList = new ArrayList<>();

        for (Post post : posts) {
            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());
            PostWriteResponseDto.WriteMain mainDto = PostWriteResponseDto.WriteMain.builder()
                    .id(post.getId())
                    .image(viewImage.getImage())
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
    public ResponseEntity<PostWriteResponseDto.WriteMain> getReview(Pageable pageable) {
        List<Post> posts = postRepository.findAllByPostCate("리뷰게시판", pageable);
        List<PostWriteResponseDto.WriteMain> reviewList = new ArrayList<>();

        for (Post post : posts) {
            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());
            PostWriteResponseDto.WriteMain mainDto = PostWriteResponseDto.WriteMain.builder()
                    .id(post.getId())
                    .image(viewImage.getImage())
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
