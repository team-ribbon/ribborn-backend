package com.spring.ribborn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Contents> contents = new ArrayList<>();

    private int likeCount;
    private int commentCount;
    private String title;
    private String category;
    private String region;
    private String item;
    private String postCate;
    private String process;

    public void setContents(Contents content){
        contents.add(content);
        content.setPost(this);
    }
}
