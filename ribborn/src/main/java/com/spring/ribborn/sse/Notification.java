package com.spring.ribborn.sse;



import com.spring.ribborn.model.TimeStamp;
import com.spring.ribborn.model.User;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Notification extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    //읽었는지에 대한 여부
    @Column(nullable = false)
    private Boolean isRead;



    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User receiver;
    //회원정보

    @Builder
    public Notification(User receiver, Boolean isRead) {
        this.receiver = receiver;
        this.isRead = isRead;
    }


    public void read() {
        this.isRead = true;
    }


//    public static Notification createOf(ChatRoom chatRoom, User user){
//
//        Notification notification = new Notification();
//
//        notification.userId = user.getId();
//        notification.changeId = chatRoom.getId();
//
//        if (chatRoom.getRequester().getId().equals(user.getId()) ){
//            notification.nickname = chatRoom.getAcceptor().getNickname();
//        } else if (chatRoom.getAcceptor().getId().equals(user.getId()) ){
//            notification.nickname = chatRoom.getRequester().getNickname();
//        }
//
//        notification.isRead = false;
//        notification.type = "TALK";

//        return notification;
//    }
//




//    public String getContent() {
//        return content.getContent();
//    }

//    public String getUrl() {
//        return url.getUrl();
//    }

}

/*
 알림 기능 처리 요소
  - 누구 : ~ 에 대한 알림이 도착했다. 형식의 알림을 클릭하면 관련 페이지로 이동하는 방식.
  - 알림을 읽으면 '읽음' 처리가 되어야한다.
 */




////import com.spring.ribborn.barter.Barter;
//import com.spring.ribborn.model.User;
//import com.spring.ribborn.websocket.chatroom.ChatRoom;
//import com.spring.ribborn.utils.CreationDate;
////import com.spring.ribborn.model.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
////import static com.spring.ribborn.websocket.chatDto.NotificationType.*;
//
//@Getter @Entity
//@NoArgsConstructor
//public class Notification extends CreationDate {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private Long changeId;
//
//    @Column(nullable = false)
//    private Long userId;
//
//    @Column(nullable = false)
//    private String nickname;
//
//    @Column(nullable = false)
//    private Boolean isRead;
//
//    @Column(nullable = false)
//    private String type;
//
////

//
//    public static Notification createOf(String nickname){
//
//        Notification notification = new Notification();
//
////        notification.userId = barter.getSellerId();
////        notification.changeId = barter.getId();
//        notification.nickname = nickname;
//        notification.isRead = false;
//        notification.type = "BARTER";
//
//        return  notification;
//    }
////
////    public static Notification createOfBarter(Barter barter, String nickname, String value, String type){
////
////        Notification notification = new Notification();
////        if (value.equals( "buyer")){
////            notification.userId = barter.getSellerId();
////        } else {
////            notification.userId = barter.getBuyerId();
////        }
////        notification.changeId = barter.getId();
////        notification.nickname = nickname;
////        notification.isRead = false;
////        if (type.equals("Barter")){
////            notification.type = FINISH;
////        } else if (type.equals("Score")) {
////            notification.type = SCORE;
////        }
////        return  notification;
////    }
//


//
//    public void setRead(){ this.isRead = true; }
//
//}