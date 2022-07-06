package com.spring.ribborn.repository;

import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.model.Images;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDetailRepository {

    private final EntityManager em;

    public PostDetailResponseDto findPostDetail(Long postId) {
        return em.createQuery(
                " select new com.spring.ribborn.dto.responseDto.PostDetailResponseDto(" +
                        "p.id, u.nickname,p.title, p.category,c.content, p.createdAt, p.modifiedAt)" +
                        " from Post p" +
                        " join User u" +
                        " join p.content c" +
                        " where p.id = :postId", PostDetailResponseDto.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

   /* public List<Images> findImages(Long postId) {

    }*/
}
