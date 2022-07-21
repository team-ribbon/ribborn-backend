package com.spring.ribborn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contents extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id")
    private Long id;

    @Nullable
    private String image;
    private String content;

    @JsonBackReference // 순환참조 방지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //이미지가 없는 Contents 생성
    public static Contents noImageContent(String content){
        Contents contents = new Contents();
        contents.setContent(content);
        return contents;
    }

    //이미지가 있는 Contents 생성
    public static Contents imageAndContent(String image, String content){
        Contents contents = new Contents();
        contents.setContent(content);
        contents.setImage(image);
        return contents;
    }

    //Contents 객체생성
    public static Contents emptyContent(){
        return new Contents();
    }

    //Contents 수정
    public void contentsChange(String image, String content){
        this.image = image;
        this.content = content;
    }
}
