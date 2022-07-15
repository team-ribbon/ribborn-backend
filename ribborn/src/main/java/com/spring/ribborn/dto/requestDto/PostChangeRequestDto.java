package com.spring.ribborn.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class PostChangeRequestDto {
    private String title;
    private String category;
    private String content;

    private List<String> fileUrl;
    private List<String> imageUrl;

    public PostChangeRequestDto(String title, String category, String content, List<String> imageUrl) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
