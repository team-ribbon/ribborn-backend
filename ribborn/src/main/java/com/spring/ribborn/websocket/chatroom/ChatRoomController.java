package com.spring.ribborn.websocket;


import com.spring.ribborn.dto.requestDto.UserRequestDto;
import com.spring.ribborn.dto.OkDto;
import com.spring.ribborn.websocket.chatDto.MessageResponseDto;
import com.spring.ribborn.websocket.chatDto.RoomResponseDto;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.websocket.chat.ChatMessageService;
import com.spring.ribborn.websocket.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService roomService;
    private final ChatMessageService messageService;


    // 채팅방 만들기
    @PostMapping("/room")
    public Long createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody UserRequestDto requestDto) {
        
        Long acceptorId = requestDto.getUserid();
        Long userid = userDetails.getUserId();
        System.out.println("------ 주는 쪽 userid = " + userid);
        System.out.println("------받는쪽  acceptorId = " + acceptorId);

        return roomService.createRoom(userid, acceptorId);
    }

    // 전체 채팅방 목록 가져오기
    @GetMapping("/rooms")
    public List<RoomResponseDto> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        String nickname = userDetails.getNickname();
        System.out.println(" 전체 채팅방 목록 가져오기 userId = " + userId);
        System.out.println(" 전체 채팅방 목록 가져오기 nickname = " + nickname);
        return roomService.getRooms(userId , nickname);
    }

    // 개별 채팅방 메시지 불러오기
    @GetMapping("/room/{roomId}")
    public List<MessageResponseDto> getMessage(@PathVariable Long roomId ,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
//                                               @PageableDefault(page = 1, size = 20) Pageable pageable) {
        Long userid = userDetails.getUserId();
        String nickname = userDetails.getNickname();
        System.out.println("개별 채팅방 메시지 가져오기 " + userid);
        System.out.println("개별 채팅방 메시지 가져오기 닉닉닉닉닉닉닉닉닉닉닉닉닉닉닉닉닉닉닉 " + nickname);
        return messageService.getMessage(roomId, userid, nickname);
    }

    // 채팅방 나가기
//    @GetMapping("/room/exit/{roomId}")
//    public ResponseEntity<OkDto> exitRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                          @PathVariable Long roomId) {
//        Long userid = userDetails.getUserId();
//        roomService.exitRoom(roomId, userid);
//        return ResponseEntity.ok().body(OkDto.valueOf("true"));
//    }

    // 채팅 즐겨찾기 고정
//    @PutMapping("/api/room/{roomId}")
//    public ResponseEntity<OkDto> fixedRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                           @PathVariable Long roomId) {
//
//        roomService.fixedRoom(roomId, userDetails);
//        return ResponseEntity.ok().body(OkDto.valueOf("true"));
//    }

    // 채팅 차단하기
//    @GetMapping("/api/room/banned/{userId}")
//    public ResponseEntity<OkDto> setBanned(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                           @PathVariable Long userId) {
//
//        roomService.setBanned(userDetails, userId);
//        return ResponseEntity.ok().body(OkDto.valueOf("true"));
//    }

    // 차단 유저 목록 보기
//    @GetMapping("/api/room/banned")
//    public List<BannedUserDto> getBanned(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return roomService.getBanned(userDetails);
//    }

    // 차단 유저 해제하기
//    @PutMapping("/api/room/banned/{userId}")
//    public ResponseEntity<OkDto> releaseBanned(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                               @PathVariable Long userId) {
//
//        roomService.releaseBanned(userDetails, userId);
//        return ResponseEntity.ok().body(OkDto.valueOf("true"));
//    }
}
