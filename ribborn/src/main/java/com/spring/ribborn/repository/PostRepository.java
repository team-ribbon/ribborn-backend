package com.spring.ribborn.repository;

import com.spring.ribborn.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllByPostCate(String postCate, Pageable pageable);
    Optional<Post> findById(Long postId);
    List<Post> findAllByPostCateOrderByCreateAtDesc(Pageable pageable,String PostCate);

}
