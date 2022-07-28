package com.spring.ribborn.websocket.chat;


//import com.spring.ribborn.websocket.NotificationRepository;
//import com.spring.ribborn.websocket.chatDto.NotificationDto;
import com.spring.ribborn.config.S3Uploader;
import com.spring.ribborn.exception.CustomException;
//import com.spring.ribborn.redis.RedisMessagePublisher;
import com.spring.ribborn.model.User;
import com.spring.ribborn.redis.RedisMessagePublisher;
import com.spring.ribborn.repository.UserRepository;
import com.spring.ribborn.sse.NotificationRepository;
import com.spring.ribborn.sse.NotificationService;
//import com.spring.ribborn.websocket.Notification;
//import com.spring.ribborn.utils.LanguageFilter;
import com.spring.ribborn.websocket.chatDto.*;
import com.spring.ribborn.websocket.chatroom.ChatRoom;
import com.spring.ribborn.websocket.chatroom.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.ribborn.exception.ErrorCode.NOT_FOUND_CHAT;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final RedisMessagePublisher redisMessagePublisher;
//    private final LanguageFilter filter;
    private final ChatRoomRepository roomRepository;
    private final ChatMessageRepository messageRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final NotificationService notificationService;
//    private final MessageRepository messageRepository1;
    private final RedisTemplate redisTemplate;
//    private final ChannelTopic channelTopic;
    private final S3Uploader s3Uploader;
    private final String imageDirName = "chatMessage";

    private Map<Long, Integer> roomUsers;

    @PostConstruct
    private void init() {
        roomUsers = new HashMap<>();
    }

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

//    region 채팅방 사진 메시지 보내기
    public void uploadChatMessageImg(File img, MessageRequestDto requestDto) {

        ChatRoom chatRoom = messageRepository.findByRoomId(requestDto.getRoomId());

        User receiver = chatRoom.getAcceptor();

        User user = userRepository.findById(requestDto.getSenderId()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 존재하지 않습니다")
        );

        String imageUrl;
        try {
            imageUrl = s3Uploader.upload(img, imageDirName);
        } catch (Exception err) {
            imageUrl = "No Message Image";
        }

        ChatMessage message = new ChatMessage(requestDto.getRoomId(), requestDto.getSenderId() , requestDto.getMessage());

        message.setImg(imageUrl);

        messageRepository.save(message);

//        MessageResponseDto messageResponseDto  = MessageResponseDto messageResponseDto.builder()
//                .roomId(message.getChatRoom().getRoomId())
//                .type(message.getType())
//                .messageId(message.getId())
//                .img(message.getImg())
//                .sender(message.getUser().getNickname())
//                .senderImg(message.getUser().getProfileImg())
//                .createdAt(TimeConversion.timeChatConversion(message.getCreateAt()))
//                .build();

        System.out.println("전송");
        // pub -> 채널 구독자에게 전달
        redisMessagePublisher.publish(requestDto);
        // 알림 보내기
        notificationService.send(receiver);
        System.out.println("성공");
    }
//    endregion

    // redis 에 저장되어 있는 message 출력
//    public List<ChatMessage> getMessages(Long roomId, Long userid , String nickname) {
//        return messageRepository1.findAllMessage(String.valueOf(roomId));
//    }

    // 채팅 메시지 및 알림 저장하기
    @Transactional
    public MessageResponseDto saveMessage(MessageRequestDto requestDto,
//                                          Long userId
                                          String username,
                                          String nickname) {
//        String topic = channelTopic.getTopic();

        MessageRequestDto sendMessageDto = new MessageRequestDto();


        System.out.println("requestDto.getRoomId() = " + requestDto.getRoomId());
        ChatRoom chatRoom = roomRepository.findByIdFetch(requestDto.getRoomId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_CHAT));

        System.out.println("chatRoomchatRoomchatRoomchatRoomchatRoomchatRoomchatRoomchatRoomchatRoom = " + chatRoom);

        User receiver = chatRoom.getAcceptor();
        ChatMessage message = messageRepository.save(ChatMessage.createOf(requestDto, username , nickname));
//        if (chatRoom.getAccOut()) {
////            // 채팅 알림 저장 및 전달하기
//            Notification notification = notificationRepository.save(Notification.createOf(chatRoom, chatRoom.getAcceptor()));
//            messagingTemplate.convertAndSend(
//                    "/sub/notification/" + chatRoom.getAcceptor().getId(), NotificationDto.createFrom(notification));
//            chatRoom.accOut(false);
//        }
//        if (chatRoom.getReqOut()) {
////            // 채팅 알림 저장 및 전달하기
//            Notification notification = notificationRepository.save(Notification.createOf(chatRoom, chatRoom.getRequester())
//            );
//            messagingTemplate.convertAndSend(
//                    "/sub/notification/" + chatRoom.getRequester().getId(), NotificationDto.createFrom(notification)
//            );
//            chatRoom.reqOut(false);
//        }
//        ObjectMapper writer = new ObjectMapper();
        // pub -> 채널 구독자에게 전달
        redisMessagePublisher.publish(requestDto);
        // 알림 보내기
        notificationService.send(receiver);
//        redisTemplate.convertAndSend(channelTopic.getTopic(), requestDto);
//        redisTemplate.convertAndSend(topic,writer.writeValueAsString(requestDto) );
        return MessageResponseDto.createOf(message, username , nickname);
    }

    // 채팅 메시지 발송하기
    public void sendMessage(MessageRequestDto requestDto, String userId, MessageResponseDto responseDto) {
        RoomMsgUpdateDto msgUpdateDto = RoomMsgUpdateDto.createFrom(requestDto);
        MessageRequestDto sendMessageDto = new MessageRequestDto();
        System.out.println(" 들어옴?들어옴?들어옴?들어옴?들어옴?들어옴?들어옴?들어옴?들어옴?들어옴?들어옴?들어옴?들어옴? " );
//        String topic = channelTopic.getTopic();
//        System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttopic = " + topic);
//        ObjectMapper writer = new ObjectMapper();
//        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwriter = " + writer);
        redisMessagePublisher.publish(requestDto);



//        notificationService.send(requestDto.getRoomId());
//        redisTemplate.convertAndSend(channelTopic.getTopic(), requestDto);
//        redisTemplate.convertAndSend(topic, writer.writeValueAsString(requestDto));
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + userId, msgUpdateDto); // 개별 채팅 목록 보기 업데이트
        messagingTemplate.convertAndSend("/sub/chat/room/" + requestDto.getRoomId(), responseDto); // 채팅방 내부로 메시지 전송
    }

}