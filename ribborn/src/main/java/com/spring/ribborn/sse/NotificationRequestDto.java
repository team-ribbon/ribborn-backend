package com.spring.ribborn.sse;


import com.spring.ribborn.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    // 받는사람 userid
    private User receiver;
//    private NotificationType notificationType;
//    private String content;
//    private String url;


}
