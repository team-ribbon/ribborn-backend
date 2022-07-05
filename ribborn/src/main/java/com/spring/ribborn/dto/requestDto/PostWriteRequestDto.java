package com.spring.ribborn.dto.requestDto;


import com.spring.ribborn.model.Content;
import com.spring.ribborn.model.Images;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostWriteRequestDto {
    private String title;
    private String category;
    private String content;
    private String username;

    private List<Images> images = new ArrayList<>();

    public PostWriteRequestDto(String title, String category, String content) {

        this.title = title;
        this.category = category;
        this.content = content;
    }

    public void settingImages(Images images){
        this.images.add(images);
    }
}
