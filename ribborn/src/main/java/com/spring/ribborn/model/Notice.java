package com.spring.ribborn.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notice extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public Notice(String title, String content) {
        this.title=title;
        this.content=content;
    }

    public void updateNotice(String title, String content){
        this.title=title;
        this.content=content;
    }
}
