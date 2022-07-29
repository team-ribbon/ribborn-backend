package com.spring.ribborn.dto.responseDto;

import lombok.Data;

@Data
public class EventResponseDto {
    private String participation;
    private String image = "https://marketkurly-imageupload.s3.ap-northeast-2.amazonaws.com/%EC%9D%B4%EB%B2%A4%ED%8A%B8.jpg";
}
