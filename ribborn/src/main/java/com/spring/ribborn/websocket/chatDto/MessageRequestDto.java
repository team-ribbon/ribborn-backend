package com.spring.ribborn.websocket.chatDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDto {
    // 메시지 타입 : 입장, 채팅
    private Long roomId;
    private Long senderId;
    private String nickname;
    private String message;
    private MessageTypeEnum type;
    private Boolean isRead;
}
