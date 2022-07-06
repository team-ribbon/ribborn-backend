package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.dto.responseDto.PostWriteResponseDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.repository.PostWriteRepository;
import com.spring.ribborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostWriteService {
    private final PostWriteRepository postWriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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
    @Transactional
    public Page<Post> getWrite(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // 후기 & 질문 상세페이지 조회
    @Transactional
    public PostWriteResponseDto.WriteDetail getDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        PostWriteResponseDto.WriteDetail detailDto = PostWriteResponseDto.WriteDetail.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .images(post.getImages())
                .title(post.getTitle())
                .category(post.getCategory())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .createAt(post.getCreateAt())
                .modifyAt(post.getModifyAt())
                .build();
        return detailDto;
    }
}
