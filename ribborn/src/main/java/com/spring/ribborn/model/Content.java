package com.spring.ribborn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Getter
public class Content {
    private String content;
    protected Content(){}

    public Content(String content){
        this.content = content;
    }
}
