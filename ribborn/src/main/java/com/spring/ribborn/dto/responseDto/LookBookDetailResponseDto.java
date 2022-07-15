package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.dto.queryDto.ContentsQueryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class LookBookDetailResponseDto {
    private Long id;
    private Long userid;
    private String nickname;
    private int likeCount;
    private boolean liked;
    private List<String> image = new ArrayList<>();
    private String category;
    private String content;
    private String introduction;
    private String addressCategory;
    private String addressDetail;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    public LookBookDetailResponseDto(Long id, Long userid, int likeCount, String nickname, String category,String introduction,String addressCategory,String addressDetail, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.userid = userid;
        this.likeCount = likeCount;
        this.nickname = nickname;
        this.introduction = introduction;
        this.addressCategory = addressCategory;
        this.addressDetail = addressDetail;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
        this.category = category;
    }

    public void contentSetting(List<ContentsQueryDto> contentsQueryDtos){
        content = contentsQueryDtos.get(0).getContent();
        for(ContentsQueryDto contentsQueryDto : contentsQueryDtos){
            image.add(contentsQueryDto.getImage());
        }
    }
}
