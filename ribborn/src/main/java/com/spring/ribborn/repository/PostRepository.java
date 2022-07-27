package com.spring.ribborn.repository;

import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllByPostCateAndCategory(String postCate, Pageable pageable, String category);
    List<Post> findAllByPostCate(String postCate, Pageable pageable);
    Optional<Post> findById(Long postId);


}
