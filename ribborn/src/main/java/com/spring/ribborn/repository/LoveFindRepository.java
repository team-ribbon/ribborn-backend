package com.spring.ribborn.repository;

import com.spring.ribborn.model.Love;
import com.spring.ribborn.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoveFindRepository extends JpaRepository<Love, Long> {
    Love findByPostIdAndUserId(Long postId, Long userId);
}
