package com.spring.ribborn.dto.responseDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class BannerDto {
    private String image;
    private String url;

    public BannerDto(String image, String url) {
        this.image = image;
        this.url = url;
    }
}
