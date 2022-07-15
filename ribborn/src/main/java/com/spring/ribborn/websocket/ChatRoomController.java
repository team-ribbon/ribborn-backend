package com.spring.ribborn.websocket;


import com.spring.ribborn.dto.requestDto.UserRequestDto;
//import com.spring.ribborn.websocket.chatDto.BannedUserDto;
import com.spring.ribborn.dto.OkDto;
import com.spring.ribborn.websocket.chatDto.MessageResponseDto;
import com.spring.ribborn.websocket.chatDto.RoomResponseDto;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.websocket.chat.ChatMessageService;
import com.spring.ribborn.websocket.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
//import org.springframework.data.web.PageableDefault;
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
        String testname = requestDto.getUsername();
        String testnick = requestDto.getNickname();
        String test1 = requestDto.getPhoneNum();

        System.out.println("test1 = " + test1);
        System.out.println("testnick = " + testnick);
        System.out.println("testname = " + testname);


        System.out.println("acceptorId = " + acceptorId);
        Long userid = userDetails.getUserId();
        return roomService.createRoom(userid, acceptorId);
    }



    // 채팅방 만들기

//    @PostMapping("/room")
//    public Long createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                           @RequestBody UserRequestDto requestDto) {
//        Long userId = userDetails.getUserId();
//        Long userIdDto = requestDto.getUserid();
////        String usernamee = userDetails.getUsername();
////        String usernameDto = requestDto.getUsername();
////        System.out.println("username = " + usernamee);
////        System.out.println("usernameDto = " + usernameDto);
//
//        System.out.println("userIdDto = " + userIdDto);
//        System.out.println("userId = " + userId);
//
//        return roomService.createRoom(userId, userIdDto);
////        return roomService.createRoom(username, requestDto);
//    }

    // 전체 채팅방 목록 가져오기
    @GetMapping("/rooms")
    public List<RoomResponseDto> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        return roomService.getRooms(userId);
    }

    // 개별 채팅방 메시지 불러오기
    @GetMapping("/room/{roomId}")
    public List<MessageResponseDto> getMessage(@PathVariable Long roomId ,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
//                                               @PageableDefault(page = 1, size = 20) Pageable pageable) {
        Long userid = userDetails.getUserId();
        System.out.println(userid);
        return messageService.getMessage(roomId, userid);
    }

    // 채팅방 나가기
    @GetMapping("/room/exit/{roomId}")
    public ResponseEntity<OkDto> exitRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long roomId) {
        Long userid = userDetails.getUserId();
        roomService.exitRoom(roomId, userid);
        return ResponseEntity.ok().body(OkDto.valueOf("true"));
    }

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
