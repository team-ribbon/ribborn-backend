package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.Images;
import lombok.Data;

import java.util.List;

@Data
public class PostImageFindDto {
    private List<Images> imagesList;

    public PostImageFindDto(List<Images> imagesList) {
        this.imagesList = imagesList;
    }
}
