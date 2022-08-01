package com.spring.ribborn.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ContentsDeleteRepository {
    private final EntityManager em;

    public void deleteContents(Long contentsId) {
        em.createQuery("delete from Contents c where c.id = :contentsId")
                .setParameter("contentsId",contentsId)
                .executeUpdate();
    }
}
