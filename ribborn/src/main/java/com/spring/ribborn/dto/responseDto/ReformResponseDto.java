package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Images;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ContentsRepository;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReformResponseDto {
    private static ContentsRepository contentsRepository;
    @Builder
    public static class ReformMain {
        private Long id;
        private Contents image;
        private String nickname;
        private String title;
        private String category;
        private String region;
        private String process;

        public static ReformMain from(Post post) {
            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtDesc(post.getId());
            return ReformMain.builder()
                    .id(post.getId())
                    .image(viewImage)
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .region(post.getRegion())
                    .process(post.getProcess())
                    .build();
        }
    }

//    @Builder
//    public static class ReformDetail {
//        private Long id;
//        private String nickname;
//        private List<Images> images;
//        private String title;
//        private String category;
//        private List<Content> content;
//        private String region;
//        private String process;
//        private LocalDateTime createAt;
//        private LocalDateTime modifyAt;
//    }
}
