package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ContentsRepository;
import lombok.*;

import java.util.List;

@Data
public class PostWriteResponseDto {
    //public static ContentsRepository contentsRepository;
    @Builder
    @Data
    public static class WritePost {
        private Long id;
        private String image;
        private int likeCount;
        private int commentCount;
        private String nickname;
        private String title;
        private String category;
        private String content;
    }
}
