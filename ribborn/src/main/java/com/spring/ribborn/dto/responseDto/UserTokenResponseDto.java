package com.spring.ribborn.dto.responseDto;

import com.spring.ribborn.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenResponseDto {
    private String username;
    private Long id;
    private String nickname;
    private int userType;
//    private int unreadMsg;

    public UserTokenResponseDto(User user) {
        this.username = user.getUsername();
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.userType = user.getUserType();
//        this.unreadMsg = getUnreadMsg();
    }
}
