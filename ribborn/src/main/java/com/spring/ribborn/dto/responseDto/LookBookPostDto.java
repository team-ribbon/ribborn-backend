package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LookBookPostDto {
    private Long id;
    private String image;
    private int likeCount;
    private int commentCount;
    private String nickname;
    private String introduction;
    private String category;
    private String content;
    private LocalDateTime createAt;

    public LookBookPostDto(Post post) {
        this.id = post.getId();
        this.image = post.getContents().get(0).getImage();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.nickname = post.getUser().getNickname();
        this.introduction = post.getIntroduction();
        this.category = post.getCategory();
        this.content = post.getContents().get(0).getContent();
        this.createAt =post.getCreateAt();
    }
}
