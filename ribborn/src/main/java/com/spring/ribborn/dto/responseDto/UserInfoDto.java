package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.User;
import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;
    private String nickname;
    private int userType;
    private String companyNum;
    private String phoneNum;
    private String addressCategory;
    private String addressDetail;
    private String introduction;

    public UserInfoDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.userType = user.getUserType();
        this.companyNum = user.getCompanyNum();
        this.phoneNum = user.getPhoneNum();
        this.addressCategory = user.getAddressCategory();
        this.addressDetail = user.getAddressDetail();
        this.introduction = user.getIntroduction();
    }
}
