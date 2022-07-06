package com.spring.ribborn.repository;

import com.spring.ribborn.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, String> {
    Images findTop1ByPostIdOrderByCreateAtDesc(Long postId);
}
