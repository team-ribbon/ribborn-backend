package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.dto.responseDto.PostWriteResponseDto;
import com.spring.ribborn.model.Images;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.ImagesRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.repository.PostWriteRepository;
import com.spring.ribborn.repository.UserRepository;
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
public class PostWriteService {
    private final PostWriteRepository postWriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImagesRepository imagesRepository;

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

    //-------------------------------------------------------------------------------------

    // 후기 & 질문 게시판 조회
    @Transactional
    public ResponseEntity<PostWriteResponseDto.WriteMain> getPostWrite(Pageable pageable, UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllByOrderByCreateAtDesc(pageable);
        List<PostWriteResponseDto.WriteMain> PostList = new ArrayList<>();

        for (Post post : posts) {
            Images viewImage = imagesRepository.findTop1ByPostIdOrderByCreateAtDesc(post.getId());
            PostWriteResponseDto.WriteMain mainDto = PostWriteResponseDto.WriteMain.builder()
                    .id(post.getId())
                    .image(viewImage)
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getCommentCount())
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .build();
            PostList.add(mainDto);
        }
        return new ResponseEntity(PostList, HttpStatus.OK);
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
