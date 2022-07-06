package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Images;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReformResponseDto {
    @Builder
    public static class ReformMain {
        private Long id;
        private Images image;
        private String nickname;
        private String title;
        private String category;
        private String region;
    }

    @Builder
    public static class ReformDetail {
        private Long id;
        private String nickname;
        private List<Images> images;
        private String title;
        private String category;
        private List<Content> content;
        private String region;
        private LocalDateTime createAt;
        private LocalDateTime modifyAt;
    }
}
