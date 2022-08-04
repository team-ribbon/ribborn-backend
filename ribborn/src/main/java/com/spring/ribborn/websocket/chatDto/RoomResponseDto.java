package com.spring.ribborn.websocket.chatDto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.spring.ribborn.websocket.chatroom.ChatRoomService.UserTypeEnum.Type.ACCEPTOR;
import static com.spring.ribborn.websocket.chatroom.ChatRoomService.UserTypeEnum.Type.REQUESTER;

@Getter
@NoArgsConstructor
public class RoomResponseDto {

    private Long roomId;
    private Long userId;
    private String nickname;
    private String senderName;
    private String message;
    private LocalDateTime date;
    private Boolean isRead;
    private Boolean isBanned;
    private int unreadCnt;
    private String type;

    public static RoomResponseDto createOf(String type, String flag, RoomDto dto, int unreadCnt, Boolean isBanned){

        RoomResponseDto responseDto = new RoomResponseDto();

        responseDto.roomId = dto.getRoomId();
        responseDto.nickname = dto.getAccNickname();
        responseDto.message = dto.getMessage();
        responseDto.date = dto.getDate();
        responseDto.isRead = dto.getIsRead();
        responseDto.isBanned = isBanned;
        responseDto.unreadCnt = unreadCnt;
        responseDto.senderName = String.valueOf(dto.getAccId());
        responseDto.type = type;

        switch ( flag ) {

            case ACCEPTOR:

                responseDto.userId = dto.getReqId();
                responseDto.nickname = dto.getReqNickname();
                break;

            case REQUESTER:

                responseDto.userId = dto.getAccId();
                responseDto.nickname = dto.getAccNickname();
                break;

            default: break;
        }

        return responseDto;
    }


}
