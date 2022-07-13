package com.spring.ribborn.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostWriteRequestDto {
    private String title;
    private String category;

    private String postCategory;

    private String content;
    private String username;
    private String region;
    private String process;

    private List<String> images = new ArrayList<>();

    public PostWriteRequestDto(String title, String category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }



}
