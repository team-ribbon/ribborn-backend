package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.BookMarkRequestDto;
import com.spring.ribborn.model.BookMark;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.BookMarkRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final PostRepository postRepository;

    @Transactional
    public void bookMarkClick(Long postId, BookMarkRequestDto bookMarkRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElse(null);
        if(bookMarkRequestDto.isBookMark()){
            //북마크 생성
            BookMark bookMark = BookMark.makeBookMark(userDetails.getUser(),post);
            bookMarkRepository.makeBookMark(bookMark);
        }else{
            //북마크 삭제
            bookMarkRepository.deleteBookMark(userDetails.getUserId(),postId);
        }
    }
}
