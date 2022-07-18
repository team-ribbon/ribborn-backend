package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.LookBookPostDto;
import com.spring.ribborn.dto.responseDto.MainPageResponseDto;
import com.spring.ribborn.dto.responseDto.MainPostDto;
import com.spring.ribborn.dto.responseDto.ReformPostDto;
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

    public MainPageResponseDto getPostList() {
        List<Post> review1 = postDetailRepository.findPostMainV2("review");
        List<Post> lookbook1 = postDetailRepository.findPostMainV2("lookbook");
        List<Post> reform1 = postDetailRepository.findPostMainV2("reform");

        List<MainPostDto> review = review1.stream()
                .map(MainPostDto::new)
                .collect(Collectors.toList());
        List<LookBookPostDto> lookbook = lookbook1.stream()
                .map(LookBookPostDto::new)
                .collect(Collectors.toList());
        List<ReformPostDto> reform = reform1.stream()
                .map(ReformPostDto::new)
                .collect(Collectors.toList());

        return new MainPageResponseDto(review,lookbook,reform);
    }
}
