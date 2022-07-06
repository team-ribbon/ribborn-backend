package com.spring.ribborn.dto.requestDto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LookBookPostWriteDto {
    private String title;
    private String category;

    private String postCategory;

    private List<String> content;
    private String username;
    private String introduction;

    private List<String> images = new ArrayList<>();

    public LookBookPostWriteDto(String title, String category, List<String> content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }


}
