package com.spring.ribborn.websocket;

import com.spring.ribborn.websocket.chatDto.MessageRequestDto;
import com.spring.ribborn.websocket.chatDto.MessageTypeEnum;
import com.spring.ribborn.utils.CreationDate;
import com.spring.ribborn.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.spring.ribborn.websocket.chatDto.MessageTypeEnum.*;

@Getter @Entity
//@JsonAutoDetect
@NoArgsConstructor
//@AllArgsConstructor
public class ChatMessage extends CreationDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    private String senderName;

    private Long senderId;

    private String senderNickname;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MessageTypeEnum type;

    @Column(nullable = false)
    private Boolean isRead;



    public static ChatMessage createOf(MessageRequestDto requestDto, String senderName, String nickname) {

        ChatMessage message = new ChatMessage();

        message.roomId = requestDto.getRoomId();
        message.senderName = senderName;
        message.message = requestDto.getMessage();
        message.isRead = requestDto.getIsRead();
        message.type = requestDto.getType();
        message.senderNickname = nickname;


        return message;
    }


    public static ChatMessage createInitOf(Long roomId) {

        ChatMessage message = new ChatMessage();

        message.roomId = roomId;
        message.senderId = roomId;
//        message.message = "채팅방이 개설되었습니다.";
        message.isRead = true;
        message.type = STATUS;

        return message;
    }

    public static ChatMessage createOutOf(Long roomId, User user) {

        ChatMessage message = new ChatMessage();

        message.roomId = roomId;
        message.senderId = roomId;
        message.message = user.getNickname() + "님이 채팅방을 나갔습니다.";
        message.isRead = true;
        message.type = STATUS;

        return message;
    }
}