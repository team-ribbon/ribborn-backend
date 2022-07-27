package com.spring.ribborn.repository;

import com.spring.ribborn.model.BookMark;
import com.spring.ribborn.model.Comment;
import com.spring.ribborn.model.Love;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkFindRepository extends JpaRepository<BookMark, Long> {
    Love findByPostIdAndUserId(Long postId, Long userId);
}
