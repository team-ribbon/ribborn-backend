package com.spring.ribborn.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.ribborn.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.spring.ribborn.model.QEvent.*;
@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Event> findEventUser(String username){
        List<Event> fetch = queryFactory
                .selectFrom(event)
                .where(event.username.eq(username))
                .fetch();

        return fetch;
    }

    public void saveEvent(Event event) {
        em.persist(event);
    }
}
