//package com.spring.ribborn.redis;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.spring.ribborn.websocket.ChatMessage;
//import com.spring.ribborn.websocket.chatDto.MessageRequestDto;
//import com.spring.ribborn.websocket.chatDto.MessageResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@RequiredArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Service
//public class RedisSubscriber implements MessageListener {
////    private final ObjectMapper objectMapper;
//    private final SimpMessageSendingOperations messagingTemplate;
//    private final RedisTemplate redisTemplate;
//    private final ObjectMapper objectMapper;
//
//    /**
//     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
//     */
//    public void onMessage(Message message, byte[] pattern) {
//
//        try {
//            // redis에서 발행된 데이터를 받아 deserialize
//            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!message = " + publishMessage);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            System.out.println("objectMapperobjectMapperobjectMapperobjectMapperobjectMapperobjectMapperobjectMapper = " + objectMapper);
//
//            MessageRequestDto messageRequestDto = objectMapper.readValue(publishMessage, MessageRequestDto.class);
//
//            System.out.println("messageRequestDtomessageRequestDtomessageRequestDtomessageRequestDtomessageRequestDto = " + messageRequestDto);
//
//            messagingTemplate.convertAndSend("/sub/chat/room/" + messageRequestDto.getRoomId(), messageRequestDto);
//            System.out.println(" 전송 완료 ");
//        } catch (Exception e) {
//             e.printStackTrace();
//            System.out.println(" no String-argument constructor/factory method to deserialize from String value  ");
//        }
//    }
//
//
//}
