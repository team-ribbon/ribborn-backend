package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private Long userid;
    private String nickname;
    private String comment;
    private LocalDateTime createAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userid = comment.getUser().getId();
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getComments();
        this.createAt = comment.getCreateAt();
    }

}
