
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

    /*private List<MainPostDto> qnaList;
    private List<MainPostDto> reviewList;
    private List<MainPostDto> lookbookList;
    private List<MainPostDto> reformList;

    public MainPageResponseDto(List<MainPostDto> qnaList, List<MainPostDto> reviewList, List<MainPostDto> lookbookList, List<MainPostDto> reformList) {
        this.qnaList = qnaList;
        this.reviewList = reviewList;
        this.lookbookList = lookbookList;
        this.reformList = reformList;
    }*/

    private List<MainPostDto> qnaList;
    private List<MainPostDto> reviewList;
    private List<MainPostDto> lookbookList;
    private List<MainPostDto> reformList;

    public MainPageResponseDto(List<MainPostDto> qnaList, List<MainPostDto> reviewList, List<MainPostDto> lookbookList, List<MainPostDto> reformList) {
        this.qnaList = qnaList;
        this.reviewList = reviewList;
        this.lookbookList = lookbookList;
        this.reformList = reformList;
    }
}

