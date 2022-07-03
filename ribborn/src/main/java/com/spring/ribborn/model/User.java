package com.spring.ribborn.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
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

}
