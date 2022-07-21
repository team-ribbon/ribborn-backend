package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.CommentWriteRequestDto;
import com.spring.ribborn.dto.responseDto.CommentResponseDto;
import com.spring.ribborn.model.Comment;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.CommentRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    //댓글 작성
    @Transactional
    public void commentWrite(Long postId, CommentWriteRequestDto commentWriteRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElse(null);
        post.commentCountUp();
        Comment comment = Comment.createComment(post,commentWriteRequestDto,userDetails.getUser());
        commentRepository.save(comment);
    }

    //댓글 페이징 조회
    public Page<CommentResponseDto> commendFind(Long postId, Pageable pageable) {
        Page<CommentResponseDto> map = commentRepository.findByPostId(postId, pageable).map(CommentResponseDto::new);
        return map;
    }

    //댓글 수정
    @Transactional
    public void commentChange(Long commentId, CommentWriteRequestDto commentWriteRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        comment.setComments(commentWriteRequestDto.getComment());
    }

    //댓글 삭제
    @Transactional
    public void commentDelete(Long commentId,Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        post.commentCountDown();
        commentRepository.deleteById(commentId);
    }
}
