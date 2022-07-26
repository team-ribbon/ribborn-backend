package com.spring.ribborn.redis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.ribborn.websocket.chatDto.MessageRequestDto;
import com.spring.ribborn.websocket.chatDto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class RedisSubscriber implements MessageListener {
//    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private final RedisTemplate redisTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
     */
    public void onMessage(Message message, byte[] pattern) {

        try {
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!message = " + publishMessage);

            ObjectMapper objectMapper = new ObjectMapper();
            // ChatMessageDto 객채로 맵핑
            MessageRequestDto messageRequestDto = objectMapper.readValue(publishMessage, MessageRequestDto.class);
//            MessageRequestDto messageRequestDto = objectMapper.convertValue(publishMessage, MessageRequestDto.class);
            // 안읽은 메세지 알람 표시 (별 모양)
//            if (roomMessage.getType().equals(MessageType.UNREAD_MESSAGE_COUNT_ALARM)) {
//                UnreadMessageCount unreadMessageCount = new UnreadMessageCount(roomMessage);
//                messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getOtherUserId(), unreadMessageCount);//채팅 메세지
//            } else {
                MessageResponseDto messageResponseDto = new MessageResponseDto(messageRequestDto);
                messagingTemplate.convertAndSend("/sub/chat/room" + messageRequestDto.getRoomId(), messageResponseDto);
                messagingTemplate.convertAndSend("/sub/chat/rooms" + messageRequestDto.getRoomId(), messageResponseDto);
//            }
            System.out.println(" 전송 완료 ");
//            messagingTemplate.convertAndSend("/topic/greetings" + messageRequestDto.getSenderId(), messageRequestDto);
        } catch (Exception e) {
             e.printStackTrace();
            System.out.println(" no String-argument constructor/factory method to deserialize from String value  ");
        }
    }


}
