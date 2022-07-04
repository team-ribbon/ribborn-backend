package com.spring.ribborn.dto;


import lombok.Data;

@Data
public class PostWriteRequestDto {
    private String title;
    private String category;
    private String content;
}
