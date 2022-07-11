
package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainPageResponseDto {


    @Builder
    @Data
    public static class mainPage {

        private List<Post> qnaList;
        private List<Post> reviewList;
        private List<Post> lookbookList;
        private List<Post> reformList;

    }



}

