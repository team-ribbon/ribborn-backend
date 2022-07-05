package com.spring.ribborn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable (name = "image", joinColumns = @JoinColumn(name="post_id",referencedColumnName = "post_id") )
    private List<Images> images = new ArrayList<>();

    @ElementCollection
    @CollectionTable (name = "content", joinColumns = @JoinColumn(name="post_id",referencedColumnName = "post_id") )
    private List<Content> content = new ArrayList<>();

    private int likeCount;
    private int commentCount;
    private String title;
    private String category;
    private String region;
    private String item;
    private String postCate;

    public void settingContent(Content content){
        this.content.add(content);
    }

}
