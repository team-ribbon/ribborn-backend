package com.spring.ribborn.repository;

import com.spring.ribborn.model.Comment;
import com.spring.ribborn.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostId(Long postId, Pageable pageable);

    void deleteAllByPost(Post post);
}
