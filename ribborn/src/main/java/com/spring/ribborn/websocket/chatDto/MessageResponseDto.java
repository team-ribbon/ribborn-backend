package com.spring.ribborn.websocket.chatDto;


import com.spring.ribborn.websocket.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageResponseDto {


    private Long messageId;
    private Long senderId;
    private String senderName;
    private String senderNickname;
    private String message;
    private LocalDateTime date;
    private Boolean isRead = false;
    private MessageTypeEnum type;

    public static MessageResponseDto createOf(ChatMessage message, String username, String nickname){

        MessageResponseDto responseDto = new MessageResponseDto();

//        responseDto.senderId = userId;
        responseDto.senderName = username;
        responseDto.messageId = message.getId();
        responseDto.message = message.getMessage();
        responseDto.date = message.getCreatedAt();
        responseDto.type = message.getType();
        responseDto.senderNickname = nickname;
//        responseDto.senderId = userid;

        return responseDto;
    }

    public static MessageResponseDto createFrom(ChatMessage message){

        MessageResponseDto responseDto = new MessageResponseDto();

        responseDto.senderId = message.getSenderId();
        responseDto.messageId = message.getId();
        responseDto.message = message.getMessage();
        responseDto.date = message.getCreatedAt();
        responseDto.type = message.getType();
        responseDto.isRead = true;
        responseDto.senderName = message.getSenderName();
        responseDto.senderNickname = message.getSenderNickname();

        return responseDto;

    }

}
