
package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainPageResponseDto {

    private String banner = "https://marketkurly-imageupload.s3.ap-northeast-2.amazonaws.com/%EC%83%81%EB%8B%A8%EC%9D%B4%EB%AF%B8%EC%A7%802.PNG";
    private Long co2Count = 156L;
    private Long co2Reduce = 1235L;
    private List<MainPostDto> reviewList;
    private List<LookBookPostDto> lookbookList;
    private List<MainPostDto> reformList;

    public MainPageResponseDto(List<MainPostDto> reviewList, List<LookBookPostDto> lookbookList, List<MainPostDto> reformList) {
        this.reviewList = reviewList;
        this.lookbookList = lookbookList;
        this.reformList = reformList;
    }
}

