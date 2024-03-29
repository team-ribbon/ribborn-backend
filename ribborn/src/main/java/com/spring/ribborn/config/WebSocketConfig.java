package com.spring.ribborn.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 구독 요청의 prefix -> /sub로 시작하도록 설정
        config.enableSimpleBroker("/sub");

        //메시지 발행 요청의 prefix -> /pub 로 시작하도록 설정
        config.setApplicationDestinationPrefixes("/pub","/sub");

    }

    // 해당 EndPoint를 통해 HandShake 하게 됩니다. 이 때, 요청을 허락하는 주소를 어디로 할 지 반드시 설정해 줘야 합니다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
        // SockJs Fallback 을 활용하여 Stomp 엔드포인트 설정
        // 메시지 발행하는 prefix /pub 로 시작 하도록 설정
        // 구독 요청의 prefix /sub 으로 시작 하도록 설정
        // 현재 엔드포인트 -> /webSocket
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler); // 핸들러 등록
    }

}
