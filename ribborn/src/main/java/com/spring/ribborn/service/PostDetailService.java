package com.spring.ribborn.service;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.repository.PostDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostDetailService {

    private final PostDetailRepository postDetailRepository;

    @Transactional
    public PostDetailResponseDto postDetailView(Long postId,int page, int size) {
        PostDetailResponseDto postDetail = postDetailRepository.findPostDetail(postId);
        List<ContentsQueryDto> contents = postDetailRepository.findContents(postId);

        postDetail.ContentSetting(contents);
        return postDetail;
    }
}
