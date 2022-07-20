package com.spring.ribborn.websocket.chat;


import com.spring.ribborn.websocket.NotificationRepository;
import com.spring.ribborn.websocket.chatDto.NotificationDto;
import com.spring.ribborn.exception.CustomException;
import com.spring.ribborn.websocket.ChatMessage;
import com.spring.ribborn.websocket.ChatRoom;
import com.spring.ribborn.websocket.Notification;
import com.spring.ribborn.utils.LanguageFilter;
import com.spring.ribborn.websocket.chatDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.ribborn.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final LanguageFilter filter;
    private final ChatRoomRepository roomRepository;
    private final ChatMessageRepository messageRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    private Map<Long, Integer> roomUsers;

    @PostConstruct
    private void init() {
        roomUsers = new HashMap<>();
    }

    // 채팅방의 상태 전달하기
//    public void sendStatus(MessageRequestDto requestDto) {
//
//        MessageTypeEnum type;
//        int count = getUserCount(requestDto); // 현재 채팅방에 접속중인 유저의 수
//
//        if (count == 2) {
//            type = FULL;
//        } else {
//            type = NORMAL;
//        }
//
//        messagingTemplate.convertAndSend("/sub/chat/room/" + requestDto.getRoomId(),
//                RoomStatusDto.valueOf(type));
//    }

    // 접속중인 유저의 수를 계산하는 메소드
//    private int getUserCount(MessageRequestDto requestDto) {
//
//        int num;
//        Long roomId = requestDto.getRoomId(); // roomId에 대한 예외처리가 필요합니다.
//
//        switch (requestDto.getType()) {
//            case IN:
//                num = 1;
//                break;
//            case OUT:
//                num = -1;
//                break;
//            default:
//                throw new IllegalArgumentException("ChatMessageService: 검증메시지 IN과 OUT만 허용됩니다.");
//        }
//        // 해시맵에 키가 존재한다면 접속중인 사람의 수를 계산합니다.
//        if (roomUsers.containsKey(roomId)) {
//
//            int userCount = roomUsers.get(roomId) + num;
//            if (userCount == 0) {
//                roomUsers.remove(roomId);
//                return 0;
//            }
//            roomUsers.put(roomId, userCount);
//        } else {
//            roomUsers.put(roomId, 1);
//        }
//
//        return roomUsers.get(roomId);
//    }

    // 메시지 찾기, 페이징 처리 (검증이 필요합니다.)
//    @Cacheable(cacheNames = "chatInfo")
    public List<MessageResponseDto> getMessage(Long roomId, Long userid , String nickname) {

        System.out.println("5555555555555555555555555555555555555 메시지찾기 getMessage nickname = " + nickname);
        // 메시지 찾아오기
        List<ChatMessage> messages = messageRepository.findAllByRoomIdOrderByIdAsc(roomId);
        // responseDto 만들기
        List<MessageResponseDto> responseDtos = new ArrayList<>();
        // 상대가 보낸 메시지라면 모두 읽음으로 처리 -> isRead 상태 모두 true로 업데이트
        messageRepository.updateChatMessage(roomId, userid);

        for (ChatMessage message : messages) {
            responseDtos.add(MessageResponseDto.createFrom(message));
        }
        return responseDtos;
    }

    // 채팅 메시지 및 알림 저장하기
    @Transactional
    public MessageResponseDto saveMessage(@RequestBody MessageRequestDto requestDto,
//                                          Long userId
                                          String username,
                                          String nickname
                                            ) {

        ChatRoom chatRoom = roomRepository.findByIdFetch(requestDto.getRoomId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_CHAT));

        // 비속어 필터링
//        requestDto = filter.filtering(requestDto

        ChatMessage message = messageRepository.save(ChatMessage.createOf(requestDto, username , nickname));

        System.out.println("============requestDto = " + requestDto + "====================");
        System.out.println("============requestDto = " + username + "====================");
//        System.out.println("============requestDto = " + userid + "====================");
        System.out.println("============requestDto = " + nickname + "====================");



        if (chatRoom.getAccOut()) {
//            // 채팅 알림 저장 및 전달하기
            Notification notification = notificationRepository.save(Notification.createOf(chatRoom, chatRoom.getAcceptor()));
            System.out.println("----------------------chatRoom.getAcceptor() = " + chatRoom.getAcceptor());
            messagingTemplate.convertAndSend(
                    "/sub/notification/" + chatRoom.getAcceptor().getId(), NotificationDto.createFrom(notification)
            );
            chatRoom.accOut(false);
        }
        if (chatRoom.getReqOut()) {
//            // 채팅 알림 저장 및 전달하기
            Notification notification = notificationRepository.save(Notification.createOf(chatRoom, chatRoom.getRequester())
            );
            System.out.println("----------------------2번째 chatRoom.getAcceptor() = " + chatRoom.getAcceptor());

            messagingTemplate.convertAndSend(
                    "/sub/notification/" + chatRoom.getRequester().getId(), NotificationDto.createFrom(notification)
            );
            System.out.println("------------------------3번째 getRequester().getId() = " + chatRoom.getRequester().getId());
            chatRoom.reqOut(false);
        }
        System.out.println("-------------------userId = " + username + "-------------------------");
        return MessageResponseDto.createOf(message, username , nickname);
    }

    // 채팅 메시지 발송하기
    public void sendMessage(MessageRequestDto requestDto, String userId, MessageResponseDto responseDto) {
        RoomMsgUpdateDto msgUpdateDto = RoomMsgUpdateDto.createFrom(requestDto);
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + userId, msgUpdateDto); // 개별 채팅 목록 보기 업데이트
        messagingTemplate.convertAndSend("/sub/chat/room/" + requestDto.getRoomId(), responseDto); // 채팅방 내부로 메시지 전송
    }
}