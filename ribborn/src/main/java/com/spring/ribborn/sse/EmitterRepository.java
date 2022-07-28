package com.spring.ribborn.websocket;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Repository
public interface EmitterRepository {
    SseEmitter save(String id, SseEmitter sseEmitter);
    void saveEventCache(String eventCacheId, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId);
    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);
    void deleteById(String id);
    Map<String, Object> findAllEventCacheStartWithId(String userId);
    void deleteAllEmitterStartWithId(String memberId);
    void deleteAllEventCacheStartWithId(String memberId);

}
