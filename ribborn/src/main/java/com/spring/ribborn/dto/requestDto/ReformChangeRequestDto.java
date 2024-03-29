package com.spring.ribborn.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class ReformChangeRequestDto {
    private String title;
    private String category;
    private String content;
    private String region;

    private List<String> fileUrl;
    private List<String> imageUrl;
    private List<String> deleteImage;

    public ReformChangeRequestDto(String title, String category, String content, List<String> imageUrl,List<String> deleteImage, String region) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.imageUrl = imageUrl;
        this.region = region;
        this.deleteImage = deleteImage;
    }
}
