package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.LookbookResponseDto;
import com.spring.ribborn.model.Images;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ImagesRepository;
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
public class LookbookService {
    private final PostRepository postRepository;
    private final ImagesRepository imagesRepository;

    // 룩북 목록페이지 조회
    @Transactional
    public ResponseEntity<LookbookResponseDto.LookbookMain> getLookbooks(Pageable pageable, UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        Long userId = userDetails.getUser().getId();
        List<LookbookResponseDto.LookbookMain> lookbookList = new ArrayList<>();

        for (Post post : posts) {
            Images viewImage = imagesRepository.findTopByPostIdOrderByCreatedAtDesc(post.getId());
            LookbookResponseDto.LookbookMain mainDto = LookbookResponseDto.LookbookMain.builder()
                    .id(post.getId())
                    .image(viewImage)
                    .nickname(post.getUser().getNickname())
                    .category(post.getCategory())
                    .likeCount(post.getLikeCount())
                    .createdAt(post.getCreateAt())
                    .build();
            lookbookList.add(mainDto);
        }
        return new ResponseEntity(lookbookList, HttpStatus.OK);
    }

    // 룩북 상세페이지 조회
    @Transactional
    public LookbookResponseDto.LookbookDetail getDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        LookbookResponseDto.LookbookDetail detailDto = LookbookResponseDto.LookbookDetail.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .images(post.getImages())
                .category(post.getCategory())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .createAt(post.getCreateAt())
                .modifyAt(post.getModifyAt())
                .build();
        return detailDto;
    }
}
