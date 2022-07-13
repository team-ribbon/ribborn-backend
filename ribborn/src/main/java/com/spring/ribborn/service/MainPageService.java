package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.MainPageResponseDto;
import com.spring.ribborn.dto.responseDto.MainPostDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.PostDetailRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final PostDetailRepository postDetailRepository;

    public MainPageResponseDto getPostList(Pageable pageable) {
        List<Post> qna1 = postDetailRepository.findPostMainV2(pageable, "qna");
        List<Post> review1 = postDetailRepository.findPostMainV2(pageable, "review");
        List<Post> lookbook1 = postDetailRepository.findPostMainV2(pageable, "lookbook");
        List<Post> reform1 = postDetailRepository.findPostMainV2(pageable, "reform");

        List<MainPostDto> qna = qna1.stream()
                .map(MainPostDto::new)
                .collect(Collectors.toList());
        List<MainPostDto> review = review1.stream()
                .map(MainPostDto::new)
                .collect(Collectors.toList());
        List<MainPostDto> lookbook = lookbook1.stream()
                .map(MainPostDto::new)
                .collect(Collectors.toList());
        List<MainPostDto> reform = reform1.stream()
                .map(MainPostDto::new)
                .collect(Collectors.toList());

        return new MainPageResponseDto(qna,review,lookbook,reform);
    }
}
