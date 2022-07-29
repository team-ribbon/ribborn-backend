package com.spring.ribborn.repository;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.ribborn.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spring.ribborn.model.QPost.*;

@Repository
@RequiredArgsConstructor
public class DynamicPostSearchRepository {
    private final JPAQueryFactory queryFactory;

    public List<Post> searchReform(Pageable pageable, String category, String process, String region){
        return queryFactory
                .selectFrom(post)
                .where(categoryEq(category), processEq(process), regionEq(region), postcateEq("reform"))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression postcateEq(String reform) {
        return post.postCate.eq(reform);
    }

    private BooleanExpression regionEq(String region) {

        return region.equals("all") ? null : post.region.eq(region);
    }

    private BooleanExpression processEq(String process) {
        return process.equals("all") ? null : post.process.eq(process);
    }

    private BooleanExpression categoryEq(String category) {
        return category.equals("all") ? null : post.category.eq(category);
    }

}
