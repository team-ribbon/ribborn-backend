package com.spring.ribborn.model;

import com.spring.ribborn.dto.requestDto.UserUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int userType;

    @Column
    private String companyNum;

    @Column
    private String phoneNum;

    @Column
    private String addressCategory;

    @Column
    private String addressDetail;

    @Column
    private String introduction;

    public User(String username, String password, String nickname,
                int userType, String companyNum, String phoneNum,
                String addressCategory, String addressDetail, String introduction) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userType = userType;
        this.companyNum = companyNum;
        this.phoneNum = phoneNum;
        this.addressCategory = addressCategory;
        this.addressDetail = addressDetail;
        this.introduction = introduction;
    }

    // 유저 정보 수정
    public void update(UserUpdateRequestDto userUpdateRequestDto) {
        this.nickname = userUpdateRequestDto.getNickname();
        this.password = userUpdateRequestDto.getNewPassword();
        this.companyNum = userUpdateRequestDto.getCompanyNum();
        this.phoneNum = userUpdateRequestDto.getPhoneNum();
        this.addressCategory = userUpdateRequestDto.getAddressCategory();
        this.addressDetail = userUpdateRequestDto.getAddressDetail();
        this.introduction = userUpdateRequestDto.getIntroduction();
    }

}
