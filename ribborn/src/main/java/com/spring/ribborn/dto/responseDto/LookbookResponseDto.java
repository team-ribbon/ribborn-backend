package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ContentsRepository;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class LookbookResponseDto {
    //public static ContentsRepository contentsRepository;
    @Builder
    @Data
    public static class Lookbook {
        private Long id;
        private String image;
        private String nickname;
        private String category;
        private int likeCount;
        private String content;
        private String introduction;
        private LocalDateTime createAt;
    }
}
