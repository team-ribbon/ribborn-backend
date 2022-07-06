package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Images;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ContentsRepository;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LookbookResponseDto {
    private static ContentsRepository contentsRepository;
    @Builder
    public static class LookbookMain {
        private Long id;
        private Contents image;
        private String nickname;
        private String category;
        private int likeCount;
        private LocalDateTime createdAt;

        public static LookbookMain from(Post post) {
            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtDesc(post.getId());
            return LookbookMain.builder()
                    .id(post.getId())
                    .image(viewImage)
                    .nickname(post.getUser().getNickname())
                    .category(post.getCategory())
                    .likeCount(post.getLikeCount())
                    .createdAt(post.getCreateAt())
                    .build();
        }
    }

    @Builder
    public static class LookbookDetail {
        private Long id;
        private String nickname;
        private List<Images> images;
        private String category;
        private List<Content> content;
        private int likeCount;
        private LocalDateTime createAt;
        private LocalDateTime modifyAt;
    }
}
