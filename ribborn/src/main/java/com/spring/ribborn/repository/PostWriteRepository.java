package com.spring.ribborn.repository;

import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Images;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
        post.setTitle(postWriteRequestDto.getTitle());
        post.setCategory(postWriteRequestDto.getCategory());
        em.persist(post);
    }
}
