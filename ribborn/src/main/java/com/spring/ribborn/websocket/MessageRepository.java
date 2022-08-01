//package com.spring.ribborn.websocket;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Slf4j
//@Repository
//public class MessageRepository {
//
//    private static final String CHAT_MESSAGE = "CHAT_MESSAGE";
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final StringRedisTemplate stringRedisTemplate;
//    private HashOperations<String, String, String> hashOpsEnterInfo;
//    private HashOperations<String, String, List<ChatMessage>> opsHashChatMessage;
//    private ValueOperations<String, String> valueOps;
//
//
//    @PostConstruct
//    private void init() {
//        opsHashChatMessage = redisTemplate.opsForHash();
//        hashOpsEnterInfo = redisTemplate.opsForHash();
//        valueOps = stringRedisTemplate.opsForValue();
//    }
//
//
//    public List<ChatMessage> findAllMessage(String roomId) {
//        return opsHashChatMessage.get(CHAT_MESSAGE, roomId);
//    }
//
//}
