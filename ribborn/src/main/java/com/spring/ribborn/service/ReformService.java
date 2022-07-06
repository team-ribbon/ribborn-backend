package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.ReformResponseDto;
import com.spring.ribborn.model.Images;
import com.spring.ribborn.model.Post;
//import com.spring.ribborn.repository.ImagesRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.security.UserDetailsImpl;
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
public class ReformService {
    private final PostRepository postRepository;
    //private final ImagesRepository imagesRepository;

    // 리폼견적 목록페이지 조회
    @Transactional
    public ResponseEntity<ReformResponseDto.ReformMain> getReforms(Pageable pageable, UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllByOrderByCreateAtDesc(pageable);
        List<ReformResponseDto.ReformMain> ReformList = new ArrayList<>();

        for (Post post : posts) {
            //Images viewImage = imagesRepository.findTop1ByPostIdOrderByCreateAtDesc(post.getId());
            ReformResponseDto.ReformMain mainDto = ReformResponseDto.ReformMain.builder()
                    .id(post.getId())
                    //.image(viewImage)
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .region(post.getRegion())
                    .build();
            ReformList.add(mainDto);
        }
        return new ResponseEntity(ReformList, HttpStatus.OK);
    }

    // 리폼견적 상세페이지 조회
    @Transactional
    public ReformResponseDto.ReformDetail getDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        ReformResponseDto.ReformDetail detailDto = ReformResponseDto.ReformDetail.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .images(post.getImages())
                .title(post.getTitle())
                .category(post.getCategory())
                .content(post.getContent())
                .region(post.getRegion())
                .createAt(post.getCreateAt())
                .modifyAt(post.getModifyAt())
                .build();
        return detailDto;
    }
}
