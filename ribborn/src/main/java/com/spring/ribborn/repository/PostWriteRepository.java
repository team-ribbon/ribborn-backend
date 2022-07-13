package com.spring.ribborn.repository;

import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
import com.spring.ribborn.model.Contents;
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
        if(postWriteRequestDto.getImages().isEmpty()){
            Contents contents = new Contents();
            contents.setContent(postWriteRequestDto.getContent());
            post.setContents(contents);
        }else{
            for(String image : postWriteRequestDto.getImages()){
                Contents contents = new Contents();
                contents.setImage(image);
                contents.setContent(postWriteRequestDto.getContent());
                post.setContents(contents);
            }
        }
        post.setUser(user);

        //post.setLikeCount();
        //post.setCommentCount();
        if(postWriteRequestDto.getRegion() != null){
            post.setRegion(postWriteRequestDto.getRegion());
        }
        if(postWriteRequestDto.getProcess() != null){
            post.setProcess(postWriteRequestDto.getProcess());
        }

        post.setPostCate(postWriteRequestDto.getPostCategory());
        post.setTitle(postWriteRequestDto.getTitle());
        post.setCategory(postWriteRequestDto.getCategory());
        em.persist(post);
    }

    public void lookBookPostWrite(LookBookPostWriteDto lookBookPostWriteDto, User user) {
        Post post = new Post();
        for(int i = 0; i < lookBookPostWriteDto.getContent().size(); i++){
            Contents contents = new Contents();
            contents.setImage(lookBookPostWriteDto.getImages().get(i));
            contents.setContent(lookBookPostWriteDto.getContent().get(i));
            post.setContents(contents);
        }
        post.setUser(user);
        post.setPostCate(lookBookPostWriteDto.getPostCategory());
        post.setCategory(lookBookPostWriteDto.getCategory());
        em.persist(post);
    }
}
