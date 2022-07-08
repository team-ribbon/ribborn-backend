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
    public static class LookbookMain {
        private Long id;
        private String image;
        private String nickname;
        private String category;
        private int likeCount;
        private LocalDateTime createAt;

//        public static LookbookMain from(Post post) {
//            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());
//            return LookbookMain.builder()
//                    .id(post.getId())
//                    .image(viewImage.getImage())
//                    .nickname(post.getUser().getNickname())
//                    .category(post.getCategory())
//                    .likeCount(post.getLikeCount())
//                    .createAt(post.getCreateAt())
//                    .build();
//        }
    }

}
