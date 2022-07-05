package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Images;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LookbookResponseDto {
    @Builder
    public static class LookbookMain {
        private Long id;
        private String nickname;
        private Images image;
        private String category;
        private int likeCount;
        private LocalDateTime createdAt;
    }

    @Builder
    public static class LookbookDetail {
        private Long id;
        private String nickname;
        private List<Images> images;
        private String category;
        private List<Content> content;
        private int likeCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
