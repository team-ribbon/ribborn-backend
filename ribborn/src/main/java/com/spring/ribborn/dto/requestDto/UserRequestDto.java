package com.spring.ribborn.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDto {
    private Long userid;
    private String username;
    private String password;
    private String nickname;
    private int userType;
    private String companyNum;
    private String phoneNum;
    private String addressCategory;
    private String addressDetail;
    private String introduction;


}


