package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.LookbookResponseDto;
import com.spring.ribborn.model.Contents;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.repository.ContentsRepository;
import com.spring.ribborn.repository.PostRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LookbookService {
    private final PostRepository postRepository;
    private final ContentsRepository contentsRepository;

    // 룩북 목록페이지 조회
//    public Page<Post> getLookbooks (Pageable pageable){
//        return postRepository.findAll(pageable);
//    }

    @Transactional
    public ResponseEntity<LookbookResponseDto.Lookbook> getLookbooks(Pageable pageable, String category) {
        List<Post> posts;
        if(category.equals("all")){
         posts = postRepository.findAllByPostCate("lookbook", pageable);
        }else{
         posts = postRepository.findAllByPostCateAndCategory("lookbook", pageable,category);
        }


        List<LookbookResponseDto.Lookbook> lookbookList = new ArrayList<>();

        for (Post post : posts) {
            /**
             * 반복문을 돌면서 쿼리가 지속 발생하는 문제 해결
             * 어차피 image와 content는 0번만 필요하기 때문에, 프록시 상태의 Contents를 get 하면서 쿼리를 발생시킨 후,
             * 이후 영속성 컨텍스트에 올라가 있는 Contents를 이용한다.
             */
            /*Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());*/
            LookbookResponseDto.Lookbook mainDto = LookbookResponseDto.Lookbook.builder()
                    .id(post.getId())
                    .image(post.getContents().get(0).getImage())
                    .content(post.getContents().get(0).getContent())
                    .introduction(post.getIntroduction())
                    .nickname(post.getUser().getNickname())
                    .category(post.getCategory())
                    .likeCount(post.getLikeCount())
                    .createAt(post.getCreateAt())
                    .build();
            lookbookList.add(mainDto);
        }
        return new ResponseEntity(lookbookList, HttpStatus.OK);
    }

}

