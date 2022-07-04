package com.spring.ribborn.model;


import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Getter
public class Images {
    private String image;

    protected Images(){}

    public Images(String image){
        this.image = image;
    }
}
