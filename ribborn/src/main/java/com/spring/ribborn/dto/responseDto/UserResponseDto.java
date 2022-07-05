package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String nickname;
    private int userType;
    private String companyNum;
    private String phoneNum;
    private String addressCategory;
    private String addressDetail;
    private String introduction;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userType = user.getUserType();
        this.nickname = user.getNickname();
        this.companyNum = user.getCompanyNum();
        this.phoneNum = user.getPhoneNum();;
        this.addressCategory = user.getAddressCategory();
        this.addressDetail = user.getAddressDetail();
        this.introduction = user.getIntroduction();
    }
}
