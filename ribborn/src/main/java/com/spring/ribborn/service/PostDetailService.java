package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.repository.PostDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostDetailService {

    private final PostDetailRepository postDetailRepository;

    public PostDetailResponseDto postDetailView(Long postId,int page, int size) {
        return postDetailRepository.findPostDetail(postId);
        //postDetailRepository.findImages(postId);
    }
}
