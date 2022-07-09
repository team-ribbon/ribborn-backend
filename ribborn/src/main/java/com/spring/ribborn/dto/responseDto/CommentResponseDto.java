package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private String nickname;
    private String comment;
    private LocalDateTime createAt;

    public CommentResponseDto(Comment comment) {
        this.nickname = comment.getWriter();
        this.comment = comment.getComments();
        this.createAt = comment.getCreateAt();
    }

}
