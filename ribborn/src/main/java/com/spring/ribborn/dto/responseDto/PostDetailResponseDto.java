package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Images;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailResponseDto {
    private Long id;
    private String nickname;
    private List<Images> images;
    private String title;
    private String category;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    private List<ReviewResponseDto> reviews;

    public PostDetailResponseDto(Long id, String nickname, String title, String category, String content, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.category = category;
        this.content = content;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }
}
