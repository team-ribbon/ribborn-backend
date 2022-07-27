package com.spring.ribborn.repository;

import com.spring.ribborn.model.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContentsRepository extends JpaRepository<Contents, Long> {
    List<Contents> findByImage(String image);
    List<Contents> findByPostId(Long postId);
    Long deleteByImage(String image);
}
