package com.spring.ribborn.repository;

import com.spring.ribborn.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images, Long> {
    Images findTop1ByPostIdOrderByCreatedAtDesc(Long postId);
}
