package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDetailResponseDto {
    private Long id;
    private Long userid;
    private String nickname;
    private Long commentCount;
    private int likeCount;
    private boolean liked;
    private boolean booked;
    private List<String> image = new ArrayList<>();
    private String title;
    private String category;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    private int totalPage;
    private int pageNumber;


    public PostDetailResponseDto(Long id,Long userid,int likeCount, String nickname, String title, String category, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.userid = userid;
        this.likeCount = likeCount;
        this.nickname = nickname;
        this.title = title;
        this.category = category;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public void contentSetting(List<ContentsQueryDto> contentsQueryDtos){
        content = contentsQueryDtos.get(0).getContent();
        for(ContentsQueryDto contentsQueryDto : contentsQueryDtos){
            image.add(contentsQueryDto.getImage());
        }
    }
}
