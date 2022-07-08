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
    public static class WriteMain {
        private Long id;
        private String image;
        private int likeCount;
        private int commentCount;
        private String nickname;
        private String title;
        private String category;


//        public static WriteMain from(Post post) {
//            //Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());
//            return WriteMain.builder()
//                    .id(post.getId())
//                    .postCate(post.getPostCate())
//                    //.image(viewImage)
//                    .likeCount(post.getLikeCount())
//                    .commentCount(post.getCommentCount())
//                    .nickname(post.getUser().getNickname())
//                    .title(post.getTitle())
//                    .category(post.getCategory())
//                    .build();
//        }

    }
}
