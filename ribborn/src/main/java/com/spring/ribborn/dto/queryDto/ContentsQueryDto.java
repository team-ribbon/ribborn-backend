package com.spring.ribborn.dto.queryDto;

import lombok.Data;

import java.util.List;

@Data
public class ContentsQueryDto {
    private String image;
    private String content;

    public ContentsQueryDto(String image, String content) {
        this.image = image;
        this.content = content;
    }
}
