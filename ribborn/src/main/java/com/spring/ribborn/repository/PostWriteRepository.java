package com.spring.ribborn.repository;

import com.spring.ribborn.dto.PostWriteRequestDto;
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
        post.setUser(user);
        post.setImages(postWriteRequestDto.getImages());
    }
}
