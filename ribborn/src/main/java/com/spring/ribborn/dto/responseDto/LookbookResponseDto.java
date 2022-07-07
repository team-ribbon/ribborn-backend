package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ContentsRepository;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class LookbookResponseDto {
    @Builder
    @Data
    public static class LookbookMain {
        private Long id;
        private Contents image;
        private String nickname;
        private String category;
        private int likeCount;
        private LocalDateTime createAt;

//        public static LookbookMain from(Post post) {
//            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtDesc(post.getId());
//            return LookbookMain.builder()
//                    .id(post.getId())
//                    .postCate(post.getPostCate())
//                    .image(viewImage)
//                    .nickname(post.getUser().getNickname())
//                    .category(post.getCategory())
//                    .likeCount(post.getLikeCount())
//                    .createdAt(post.getCreateAt())
//                    .build();
//        }
    }

}
