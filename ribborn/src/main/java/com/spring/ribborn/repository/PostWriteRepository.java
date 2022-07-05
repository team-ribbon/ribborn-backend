package com.spring.ribborn.repository;

import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostWriteRepository {

    private final EntityManager em;
    public void postWrite(PostWriteRequestDto postWriteRequestDto, User user) {
        Post post = new Post();
        Content content = new Content(postWriteRequestDto.getContent());
        post.setUser(user);
        post.setImages(postWriteRequestDto.getImages());
        post.settingContent(content);
        //post.setLikeCount();
        //post.setCommentCount();
        if(postWriteRequestDto.getRegion() != null){
            post.setRegion(postWriteRequestDto.getRegion());
        }
        post.setPostCate(postWriteRequestDto.getPostCategory());
        post.setTitle(postWriteRequestDto.getTitle());
        post.setCategory(postWriteRequestDto.getCategory());
        em.persist(post);
    }

    public void lookBookPostWrite(LookBookPostWriteDto lookBookPostWriteDto, User user) {
        Post post = new Post();
        List<Content> contents = lookBookPostWriteDto.getContent();
        post.setContent(contents);
        post.setUser(user);
        post.setImages(lookBookPostWriteDto.getImages());
        post.setPostCate(lookBookPostWriteDto.getPostCategory());
        post.setCategory(lookBookPostWriteDto.getCategory());
        em.persist(post);
    }
}
