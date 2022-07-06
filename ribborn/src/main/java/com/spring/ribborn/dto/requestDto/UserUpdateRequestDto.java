package com.spring.ribborn.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {

    private String nickname;
    private String currentPassword;
    private String newPassword;
    private String companyNum;
    private String phoneNum;
    private String addressCategory;
    private String addressDetail;
    private String introduction;
}
