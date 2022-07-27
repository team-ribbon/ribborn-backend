package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReformPostDetailResponseDto {
    private Long id;

    private Long userid;
    private String nickname;
    private List<String> image = new ArrayList<>();
    private String title;
    private String category;
    private String content;
    private boolean booked;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;
    private String region;
    private String process;


    public ReformPostDetailResponseDto(Long id, Long userid, String nickname, String title, String category, LocalDateTime createAt, LocalDateTime modifyAt, String region, String process) {
        this.id = id;
        this.userid = userid;
        this.nickname = nickname;
        this.title = title;
        this.category = category;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
        this.region = region;
        this.process = process;
    }
    public void contentSetting(List<ContentsQueryDto> contentsQueryDtos){
        content = contentsQueryDtos.get(0).getContent();
        for(ContentsQueryDto contentsQueryDto : contentsQueryDtos){
            image.add(contentsQueryDto.getImage());
        }
    }
}
