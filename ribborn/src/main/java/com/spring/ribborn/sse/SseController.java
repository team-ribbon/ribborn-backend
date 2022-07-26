package com.spring.ribborn.sse;



import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class SseController {
    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();
    Long timeout = 60L * 1000L * 60L; // 1시간

    @GetMapping(value = "/api/subscribe/{id}" , produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable String id) {
        SseEmitter emitter = new SseEmitter(timeout);
        CLIENTS.put(id, emitter);

        emitter.onTimeout(() -> CLIENTS.remove(id));
        emitter.onCompletion(() -> CLIENTS.remove(id));
        return emitter;
    }

}