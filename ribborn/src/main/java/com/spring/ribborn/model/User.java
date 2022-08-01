package com.spring.ribborn.model;

import com.spring.ribborn.dto.requestDto.UserUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User implements Serializable {

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

    //일반 유저 생성
    public static User createGeneralUser(String username, String password, String nickname, int userType, String phoneNum){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setUserType(userType);
        user.setPhoneNum(phoneNum);
        return user;
    }

    //기술자 유저 생성
    public static User createExpertUser(String username, String password, String nickname, int userType, String phoneNum, String companyNum, String addressCategory, String addressDetail, String introduction){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setUserType(userType);
        user.setPhoneNum(phoneNum);
        user.setCompanyNum(companyNum);
        user.setAddressCategory(addressCategory);
        user.setAddressDetail(addressDetail);
        user.setIntroduction(introduction);
        return user;
    }

    //User Introduction 설정 메서드
    public void settingIntroduction(String introduction){
        this.introduction = introduction;
    }

    public void updateUser(String password, String nickname, String companyNum, String phoneNum, String addressCategory, String addressDetail, String introduction){
        if(password != null){
            this.password = password;
        }
        if(nickname != null){
            this.nickname = nickname;
        }
        if(companyNum != null){
            this.companyNum = companyNum;
        }
        if(phoneNum != null){
            this.phoneNum = phoneNum;
        }
        if(addressCategory != null){
            this.addressCategory = addressCategory;
        }
        if(addressDetail != null){
            this.addressDetail = addressDetail;
        }
        if(introduction != null){
            this.introduction = introduction;
        }

    }
    /*public User(String username, String password, String nickname,
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
    }*/

    /*// 유저 정보 수정
    public void update(UserUpdateRequestDto userUpdateRequestDto) {
        this.nickname = userUpdateRequestDto.getNickname();
        this.password = userUpdateRequestDto.getNewPassword();
        this.companyNum = userUpdateRequestDto.getCompanyNum();
        this.phoneNum = userUpdateRequestDto.getPhoneNum();
        this.addressCategory = userUpdateRequestDto.getAddressCategory();
        this.addressDetail = userUpdateRequestDto.getAddressDetail();
        this.introduction = userUpdateRequestDto.getIntroduction();
    }*/

}
