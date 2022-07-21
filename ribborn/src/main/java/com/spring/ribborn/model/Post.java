package com.spring.ribborn.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //@JsonIgnore
    @JsonManagedReference // 직렬화 허용 어노테이션
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
    private String introduction;


    public static Post createNormalPost(User user, String region, String process, String postCate, String title, String category){
        Post post = new Post();
        post.setUser(user);
        post.setRegion(region);
        post.setProcess(process);
        post.setPostCate(postCate);
        post.setTitle(title);
        post.setCategory(category);
        return post;
    }

    public static Post createLookBookPost(User user,String postCate, String category, String introduction){
        Post post = new Post();
        post.setUser(user);
        post.setPostCate(postCate);
        post.setCategory(category);
        post.setIntroduction(introduction);
        return post;
    }

    //content 셋팅
    public void settingContents(Contents content){
        contents.add(content);
        content.setPost(this);
    }

    //게시글 수정
    public void normalPostChange(String title, String category){
        this.title = title;
        this.category = category;
    }

    //리폼 게시글 수정
    public void reformPostChange(String title, String category, String region) {
        this.title = title;
        this.category = category;
        this.region = region;
    }

    //commentCountUp
    public void commentCountUp(){
        this.commentCount += 1;
    }

    //commentCountDown
    public void commentCountDown(){
        this.commentCount -= 1;
    }

    //likeCountUp
    public void likeCountUp(){
        this.likeCount += 1;
    }

    //likeCountDown
    public void likeCountDown(){
        this.likeCount -= 1;
    }


}
