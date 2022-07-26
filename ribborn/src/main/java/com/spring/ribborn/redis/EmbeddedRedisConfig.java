package com.spring.ribborn.redis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/*
    채팅 서버 실행 시 Embedded Redis 서버도 동시에 실행되도록 설정해야한다.
    local 환경에서만 실행되도록 @Profile 선언
 */
@Profile("13.209.15.135")
@Configuration
public class EmbeddedRedisConfig {
    @Value("${spring.redis.port}")
    private int redisPort;
    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(redisPort);
        System.out.println(" 레디스 시작!~!!!!!!!!!!!!!!!! ");
        try {
            redisServer.start();
        } catch (Exception e) {
            System.out.println("에러발생 redis server 2개 실행하려고 했어요");
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}