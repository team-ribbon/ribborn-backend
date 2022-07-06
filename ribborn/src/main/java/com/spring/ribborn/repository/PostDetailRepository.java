package com.spring.ribborn.repository;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
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
                        "p.id, u.nickname,p.title, p.category,p.createAt,p.modifyAt)" +
                        " from Post p" +
                        " join p.user u" +
                        " where p.id = :postId", PostDetailResponseDto.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    public List<ContentsQueryDto> findContents(Long postId) {
        return em.createQuery(
                " select new com.spring.ribborn.dto.queryDto.ContentsQueryDto(" +
                        " c.image,c.content)" +
                        " from Contents c" +
                        " join c.post p" +
                        " where p.id = :postId", ContentsQueryDto.class)
                .setParameter("postId",postId)
                .getResultList();
    }

   /* public List<Images> findImages(Long postId) {

    }*/
}
