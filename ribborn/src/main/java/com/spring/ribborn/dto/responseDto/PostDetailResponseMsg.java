package com.spring.ribborn.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class PostDetailResponseMsg {
    private PostDetailResponseDto post;
    private List<CommentResponseDto> comment;
}
