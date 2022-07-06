package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDetailResponseDto {
    private Long id;
    private String nickname;
    private List<String> images = new ArrayList<>();
    private String title;
    private String category;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    private List<ReviewResponseDto> reviews;

    public PostDetailResponseDto(Long id, String nickname, String title, String category, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.category = category;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public void ContentSetting(List<ContentsQueryDto> contentsQueryDtos){
        content = contentsQueryDtos.get(0).getContent();
        System.out.println(content);
        for(ContentsQueryDto contentsQueryDto : contentsQueryDtos){
            System.out.println(contentsQueryDto);
            images.add(contentsQueryDto.getImage());
        }
    }
}
