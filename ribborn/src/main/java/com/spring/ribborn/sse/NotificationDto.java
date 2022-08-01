package com.spring.ribborn.sse;



import com.spring.ribborn.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NotificationDto {


    private Long id;
//
//    private String content;
//
//    private String url;
//
//    private Boolean status;



    public static NotificationDto create(Notification notification) {
        return new NotificationDto(notification.getId());
    }




    public static NotificationDto createOf(Notification notification, User user){

        NotificationDto dto = new NotificationDto();

        dto.id = notification.getId();
//        dto.get = notification.getNickname();
//        dto.isRead = notification.getIsRead();
//        dto.type = notification.getType();
//        dto.date = notification.getCreatedAt();

//        dto.userId = user.getId();
//        dto.profile = user.getProfile();

        return dto;
    }


}
//
//import com.spring.ribborn.model.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter @Setter
//@NoArgsConstructor
//public class NotificationDto {
//
//    private Long notificationId;
////    private Long changeId;
//    private String nickname;
//    private Boolean isRead;
//    private String type;
//    private LocalDateTime date;
////    private List<ItemStarDto> itemList;
//    private Long userId;
////    private String profile;
//
//    public static NotificationDto createFrom(Notification notification){
//
//        NotificationDto dto = new NotificationDto();
//
//        dto.notificationId = notification.getId();
//        dto.nickname = notification.getNickname();
//        dto.isRead = notification.getIsRead();
//        dto.type = notification.getType();
//        dto.date = notification.getCreatedAt();
//
//        return dto;
//    }
////
////    public static NotificationDto createFrom(Notification notification, List<ItemStarDto> itemList){
////
////        NotificationDto dto = new NotificationDto();
////
////        dto.notificationId = notification.getId();
////        dto.changeId = notification.getChangeId();
////        dto.nickname = notification.getNickname();
////        dto.isRead = notification.getIsRead();
////        dto.type = notification.getType();
////        dto.date = notification.getCreatedAt();
////        dto.itemList = itemList;
////
////        return dto;
////    }
//

//
//    public static Object from(Notification notification) {
//
//        NotificationDto dto = new NotificationDto();
//
//        dto.notificationId = notification.getId();
//        dto.nickname = notification.getNickname();
//        dto.isRead = notification.getIsRead();
//        dto.type = notification.getType();
//        dto.date = notification.getCreatedAt();
//
//        return dto;
//    }
//}