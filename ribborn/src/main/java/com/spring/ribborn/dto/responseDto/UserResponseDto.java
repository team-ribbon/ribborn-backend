package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {


    private UserInfoDto users;
    private List<MainPostDto> posts;
    private List<MainPostDto> qnaList;
    private List<MainPostDto> reviewList;
    private List<MainPostDto> lookbookList;
    private List<MainPostDto> reformList;


}
