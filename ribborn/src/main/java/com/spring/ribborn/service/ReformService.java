package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.ReformResponseDto;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;;
import com.spring.ribborn.repository.ContentsRepository;
import com.spring.ribborn.repository.PostRepository;
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
public class ReformService {
    private final PostRepository postRepository;
    private final ContentsRepository contentsRepository;

    // 리폼견적 목록페이지 조회
//    @Transactional
//    public Page<Post> getReforms(Pageable pageable){
//        return postRepository.findAll(pageable);
//
//    }

    @Transactional
    public ResponseEntity<ReformResponseDto.Reform> getReforms(Pageable pageable) {
        List<Post> posts = postRepository.findAllByPostCate("리폼게시판", pageable);
        List<ReformResponseDto.Reform> ReformList = new ArrayList<>();

        for (Post post : posts) {
            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());
            ReformResponseDto.Reform mainDto = ReformResponseDto.Reform.builder()
                    .id(post.getId())
                    .image(viewImage.getImage())
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .region(post.getRegion())
                    .build();
            ReformList.add(mainDto);
        }
        return new ResponseEntity(ReformList, HttpStatus.OK);
    }

}

