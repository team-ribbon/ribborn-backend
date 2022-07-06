package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Images;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostWriteResponseDto {
    @Builder
    public static class WriteMain {
        private Long id;
        private Images image;
        private int likeCount;
        private int commentCount;
        private String nickname;
        private String title;
        private String category;
    }

    @Builder
    public static class WriteDetail {
        private Long id;
        private String nickname;
        private List<Images> images;
        private String title;
        private String category;
        private List<Content> content;
        private int likeCount;
        private LocalDateTime createAt;
        private LocalDateTime modifyAt;
    }
}