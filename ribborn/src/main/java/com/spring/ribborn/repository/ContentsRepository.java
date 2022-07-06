package com.spring.ribborn.repository;

import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContentsRepository extends JpaRepository<Contents, Long> {
    Contents findTop1ByPostIdOrderByCreateAtDesc(Long postId);
}
