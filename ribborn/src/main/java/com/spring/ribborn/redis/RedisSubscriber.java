package com.spring.ribborn.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ribborn.websocket.chatDto.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;


    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
     */
    public void sendMessage(String publishMessage) {
        try {
            // ChatMessage 객채로 맵핑
            MessageRequestDto messageRequestDto = objectMapper.readValue(publishMessage, MessageRequestDto.class);
            // 채팅방을 구독한 클라이언트에게 메시지 발송(메인페이지 채팅방 번호 pid 필요?)
            messagingTemplate.convertAndSend("/topic/greetings" + messageRequestDto.getSenderId(), messageRequestDto);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
