package com.spring.ribborn.model;

import com.spring.ribborn.dto.requestDto.CommentWriteRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String comments;


    public static Comment createComment(Post post, CommentWriteRequestDto commentWriteRequestDto, User user){
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setComments(commentWriteRequestDto.getComment());
        comment.setUser(user);
        return comment;
    }

}
