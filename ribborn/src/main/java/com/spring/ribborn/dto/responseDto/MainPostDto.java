package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainPostDto {
    private Long id;
    private String image;
    private int likeCount;
    private int commentCount;
    private String nickname;
    private String title;
    private String category;
    private String content;
    private String process;
    private LocalDateTime createAt;

    public MainPostDto(Post post) {
        this.id = post.getId();
        this.image = post.getContents().get(0).getImage();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.process = post.getProcess();
        this.category = post.getCategory();
        this.content = post.getContents().get(0).getContent();
        this.createAt = post.getCreateAt();
    }
}
