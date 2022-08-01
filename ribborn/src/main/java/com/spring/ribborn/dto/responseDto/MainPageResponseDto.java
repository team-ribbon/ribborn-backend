
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

    private List<BannerDto> banner = new ArrayList<>();
    private String spinner = "https://marketkurly-imageupload.s3.ap-northeast-2.amazonaws.com/%EC%8A%A4%ED%94%BC%EB%84%88.png";
    private Long co2Count = 156L;
    private Long co2Reduce = 1235L;
    private List<MainPostDto> reviewList;
    private List<LookBookPostDto> lookbookList;
    private List<ReformPostDto> reformList;

    public MainPageResponseDto(List<MainPostDto> reviewList, List<LookBookPostDto> lookbookList, List<ReformPostDto> reformList) {
        this.reviewList = reviewList;
        this.lookbookList = lookbookList;
        this.reformList = reformList;
        bannerSetting();
    }

    public void bannerSetting(){
        BannerDto bannerDto = new BannerDto("https://marketkurly-imageupload.s3.ap-northeast-2.amazonaws.com/%EC%9A%B0%EC%88%98%EB%B0%B0%EB%84%88.jpg","/event/1");
        BannerDto bannerDto2 = new BannerDto("https://marketkurly-imageupload.s3.ap-northeast-2.amazonaws.com/KakaoTalk_20220727_214101460_01.png","https://forms.gle/sirNtEE3XjSEJVEt6");
        this.banner.add(bannerDto);
        this.banner.add(bannerDto2);
    }
}

