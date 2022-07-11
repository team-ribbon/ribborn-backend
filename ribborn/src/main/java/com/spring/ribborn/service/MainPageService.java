package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.MainPageResponseDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final PostRepository postRepository;

    public ResponseEntity getPostList(Pageable pageable, UserDetailsImpl userDetails) {
        List<Post> qnaposts = postRepository.findAllByPostCateOrderByCreateAtDesc(pageable,"질문게시판");
        List<Post> reviewposts = postRepository.findAllByPostCateOrderByCreateAtDesc(pageable,"리뷰게시판");
        List<Post> lookposts = postRepository.findAllByPostCateOrderByCreateAtDesc(pageable,"룩북게시판");
        List<Post> reformposts = postRepository.findAllByPostCateOrderByCreateAtDesc(pageable,"리폼게시판");

        MainPageResponseDto.mainPage mainDto = MainPageResponseDto.mainPage.builder()
                .qnaList(qnaposts)
                .lookbookList(lookposts)
                .reformList(reformposts)
                .reviewList(reviewposts)
                .build();

       return new ResponseEntity(mainDto, HttpStatus.OK);

    }
}
