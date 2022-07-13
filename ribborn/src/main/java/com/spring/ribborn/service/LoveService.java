package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LoveRequestDto;
import com.spring.ribborn.model.Love;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.LoveRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoveService {
    private final LoveRepository loveRepository;
    private final PostRepository postRepository;
    @Transactional
    public void loveClick(Long postId, LoveRequestDto loveRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElse(null);
        if(loveRequestDto.isLove()){
            //좋아요 저장

            post.setLikeCount(post.getLikeCount()+1);
            Love love = Love.makeLove(userDetails.getUser(),post);
            loveRepository.makeLove(love);
        }else{
            //좋아요 삭제
            post.setLikeCount(post.getLikeCount()-1);
            loveRepository.deleteLove(userDetails.getUserId(),postId);
        }
    }
}
