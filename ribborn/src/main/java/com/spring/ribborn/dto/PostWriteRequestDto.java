package com.spring.ribborn.dto;


import lombok.Data;

import java.util.List;

@Data
public class PostWriteRequestDto {
    private String title;
    private String category;
    private String content;

    private String nickname;
    private String username;

    private List<String> images;

    public PostWriteRequestDto(String title, String category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
