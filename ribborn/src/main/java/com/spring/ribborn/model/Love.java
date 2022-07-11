package com.spring.ribborn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Love {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "love_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Love makeLove(User user, Post post){
        Love love = new Love();
        love.setUser(user);
        love.setPost(post);
        return love;
    }
}
