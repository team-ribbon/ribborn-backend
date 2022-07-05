package com.spring.ribborn.repository;

import com.spring.ribborn.model.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<Post> findById(Long postId);
}
