package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ContentsRepository;
import lombok.Builder;
import lombok.Data;

@Data
public class ReformResponseDto {
    //private static ContentsRepository contentsRepository;
    @Builder
    @Data
    public static class Reform {
        private Long id;
        private String image;
        private String nickname;
        private String title;
        private String category;
        private String region;
        private String process;
        private String content;

//        public static Reform from(Post post) {
//            Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());
//            return ReformMain.builder()
//                    .id(post.getId())
//                    .image(viewImage.getImage())
//                    .nickname(post.getUser().getNickname())
//                    .title(post.getTitle())
//                    .category(post.getCategory())
//                    .region(post.getRegion())
//                    .process(post.getProcess())
//                    .build();
//        }
    }
}
