package com.spring.ribborn.repository;

import com.spring.ribborn.model.Love;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LoveRepository {
    private final EntityManager em;
    public void makeLove(Love love) {
        em.persist(love);
    }

    public void deleteLove(Long userId, Long postId) {
        em.createQuery("delete from Love l where l.user.id = :userId and l.post.id = :postId")
                .setParameter("userId",userId)
                .setParameter("postId",postId)
                .executeUpdate();
    }
}
