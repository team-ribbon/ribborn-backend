package com.spring.ribborn.repository;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.responseDto.LookBookDetailResponseDto;
import com.spring.ribborn.dto.responseDto.MainPostDto;
import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.dto.responseDto.ReformPostDetailResponseDto;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDetailRepository {

    private final EntityManager em;
    private final AwsS3Service awsS3Service;

    //게시글 찾기
    public PostDetailResponseDto findPostDetail(Long postId) {
        return em.createQuery(
                " select new com.spring.ribborn.dto.responseDto.PostDetailResponseDto(" +
                        "p.id,u.id,p.likeCount, u.nickname,p.title, p.category,p.createAt,p.modifyAt)" +
                        " from Post p" +
                        " join p.user u" +
                        " where p.id = :postId", PostDetailResponseDto.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    //게시글 찾기

    public List<Post> findPostMainV2(String postCate) {
        return em.createQuery(
                "select distinct p from Post p" +
                        " join fetch p.user u" +
                        " join fetch p.contents c" +
                        " where p.postCate = :postCate" +
                        " order by p.likeCount desc ", Post.class)
                .setParameter("postCate", postCate)
                .setFirstResult(0)
                .setMaxResults(6)
                .getResultList();
    }

    public List<Post> findMyPost(String postCate, Long id) {
        return em.createQuery(
                        "select distinct p from Post p" +
                                " join fetch p.user u" +
                                " join fetch p.contents c" +
                                " where p.postCate = :postCate and u.id = :id" +
                                " order by p.id desc ", Post.class)
                .setParameter("postCate", postCate)
                .setParameter("id", id)
                .getResultList();
    }

    //리폼 게시글 찾기
    public ReformPostDetailResponseDto reformPostDetail(Long postId) {
        return em.createQuery(
                        " select new com.spring.ribborn.dto.responseDto.ReformPostDetailResponseDto(" +
                                "p.id,u.id, u.nickname,p.title, p.category,p.createAt,p.modifyAt,p.region,p.process)" +
                                " from Post p" +
                                " join p.user u" +
                                " where p.id = :postId", ReformPostDetailResponseDto.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    //룩북 게시글 찾기
    public LookBookDetailResponseDto lookBookPostDetail(Long postId) {
        return em.createQuery(
                        " select new com.spring.ribborn.dto.responseDto.LookBookDetailResponseDto(" +
                                "p.id,u.id,p.likeCount,u.nickname,p.category,p.introduction,u.addressCategory,u.addressDetail,p.createAt,p.modifyAt)" +
                                " from Post p" +
                                " join p.user u" +
                                " where p.id = :postId", LookBookDetailResponseDto.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }
    //게시글 컨텐츠 찾기
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

}
