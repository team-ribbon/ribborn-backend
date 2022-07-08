package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class LookBookDetailResponseDto {
    private Long id;
    private String nickname;

    private List<String> image = new ArrayList<>();
    private String category;
    private List<String> content = new ArrayList<>();
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    public LookBookDetailResponseDto(Long id, String nickname, String category, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.nickname = nickname;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
        this.category = category;
    }

    public void contentSetting(List<ContentsQueryDto> contentsQueryDtos){

        for(ContentsQueryDto contentsQueryDto : contentsQueryDtos){
            content.add(contentsQueryDto.getContent());
            image.add(contentsQueryDto.getImage());
        }
    }
}