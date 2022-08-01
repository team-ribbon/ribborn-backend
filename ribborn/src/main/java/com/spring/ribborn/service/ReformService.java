package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.ReformResponseDto;
import com.spring.ribborn.model.Post;;
import com.spring.ribborn.repository.ContentsRepository;
import com.spring.ribborn.repository.DynamicPostSearchRepository;
import com.spring.ribborn.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReformService {
    private final PostRepository postRepository;
    private final ContentsRepository contentsRepository;

    private final DynamicPostSearchRepository dynamicPostSearchRepository;



    // 리폼견적 목록페이지 조회
//    @Transactional
//    public Page<Post> getReforms(Pageable pageable){
//        return postRepository.findAll(pageable);
//
//    }

    @Transactional
    public ResponseEntity<ReformResponseDto.Reform> getReforms(Pageable pageable, String category, String process, String region) {
        List<Post> posts;
        /*if(category.equals("all")){
            posts = postRepository.findAllByPostCate("reform", pageable);
        }else{
            posts = postRepository.findAllByPostCateAndCategory("reform", pageable,category);
        }*/

        posts = dynamicPostSearchRepository.searchReform(pageable,category,process,region);

        List<ReformResponseDto.Reform> ReformList = new ArrayList<>();

        for (Post post : posts) {
            /**
             * 반복문을 돌면서 쿼리가 지속 발생하는 문제 해결
             * 어차피 image와 content는 0번만 필요하기 때문에, 프록시 상태의 Contents를 get 하면서 쿼리를 발생시킨 후,
             * 이후 영속성 컨텍스트에 올라가 있는 Contents를 이용한다.
             */
            /*Contents viewImage = contentsRepository.findTop1ByPostIdOrderByCreateAtAsc(post.getId());*/
            ReformResponseDto.Reform mainDto = ReformResponseDto.Reform.builder()
                    .id(post.getId())
                    .image(post.getContents().get(0).getImage())
                    .content(post.getContents().get(0).getContent())
                    .process(post.getProcess())
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .region(post.getRegion())
                    .build();
            ReformList.add(mainDto);
        }
        return new ResponseEntity(ReformList, HttpStatus.OK);
    }

}

