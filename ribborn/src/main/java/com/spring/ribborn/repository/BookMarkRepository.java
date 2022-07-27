package com.spring.ribborn.repository;

import com.spring.ribborn.model.BookMark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


@Repository
@RequiredArgsConstructor
public class BookMarkRepository {

    private final EntityManager em;

    public void makeBookMark(BookMark bookMark) {
        em.persist(bookMark);
    }

    public void deleteBookMark(Long userId, Long postId) {
        em.createQuery("delete from BookMark b where b.user.id = :userId and b.post.id = :postId")
                .setParameter("userId",userId)
                .setParameter("postId",postId)
                .executeUpdate();
    }
}
