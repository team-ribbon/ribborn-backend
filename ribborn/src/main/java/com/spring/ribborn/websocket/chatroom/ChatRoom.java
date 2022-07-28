package com.spring.ribborn.websocket.chatroom;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.ribborn.model.TimeStamp;
import com.spring.ribborn.model.User;
import lombok.Getter;

import javax.persistence.*;

import static com.spring.ribborn.websocket.chatroom.ChatRoomService.UserTypeEnum.Type.ACCEPTOR;
import static com.spring.ribborn.websocket.chatroom.ChatRoomService.UserTypeEnum.Type.REQUESTER;

@Getter @Entity
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
//@NoArgsConstructor
public class ChatRoom extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User acceptor;

    @Column(nullable = false)
    private Boolean reqOut;

    @Column(nullable = false)
    private Boolean accOut;

    @Column(nullable = false)
    private Boolean accFixed;

    @Column(nullable = false)
    private Boolean reqFixed;

    public ChatRoom(){

    }

    public static ChatRoom createOf(User requester, User acceptor) {

        ChatRoom room = new ChatRoom();
        room.requester = requester;
        room.acceptor = acceptor;
        room.reqOut = false;
        room.accOut = true;
        room.accFixed = false;
        room.reqFixed = false;
        return room;
    }

    public void reqOut(Boolean bool) {
        this.reqOut = bool;
    }

    public void accOut(Boolean bool) {
        this.accOut = bool;
    }

    public void fixedRoom(String flag) {

        switch (flag) {
            case ACCEPTOR:
                this.accFixed = true;
                break;
            case REQUESTER:
                this.reqFixed = true;
                break;
            default:
                throw new IllegalArgumentException("ChatRoom: 올바른 인자값을 입력해 주세요.(ACCEPTOR/REQUESTER)");
        }
    }

    public void enter() {
        this.accOut = false;
        this.reqOut = false;
    }
}