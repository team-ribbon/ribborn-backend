package com.spring.ribborn.repository;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import com.spring.ribborn.dto.responseDto.LookBookDetailResponseDto;
import com.spring.ribborn.dto.responseDto.PostDetailResponseDto;
import com.spring.ribborn.dto.responseDto.ReformPostDetailResponseDto;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
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
                        "p.id, u.nickname,p.title, p.category,p.createAt,p.modifyAt)" +
                        " from Post p" +
                        " join p.user u" +
                        " where p.id = :postId", PostDetailResponseDto.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    //리폼 게시글 찾기
    public ReformPostDetailResponseDto reformPostDetail(Long postId) {
        return em.createQuery(
                        " select new com.spring.ribborn.dto.responseDto.ReformPostDetailResponseDto(" +
                                "p.id, u.nickname,p.title, p.category,p.createAt,p.modifyAt,p.region,p.process)" +
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
                                "p.id, u.nickname,p.category,p.createAt,p.modifyAt)" +
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


    //게시글 삭제
    public void deletePost(Long postId) {
        Post post = em.find(Post.class, postId);
        for(Contents con : post.getContents()){
            awsS3Service.deleteFile(con.getImage());
        }
        em.remove(post);
    }
}
