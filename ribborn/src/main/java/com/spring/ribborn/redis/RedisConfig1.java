package com.spring.ribborn.redis;//package com.spring.ribborn.redis;
//
//
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.io.Serializable;
//
//@Configuration
//@EnableRedisRepositories
//@EnableCaching
//public class RedisConfig {
//
//    @Value("${spring.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.redis.port}")
//    private int redisPort;
//
//    /**
//     * redis pub/sub 메시지를 처리하는 listener 설정
//     */
//    @Bean
//    public RedisMessageListenerContainer redisMessageListener(
//            RedisConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapter,
//            ChannelTopic channelTopic) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        // 방마다 topic을 Repository에 저장할 필요 없이 channelTopic을 DI로 받아 사용가능하게 만듦
//        container.addMessageListener(listenerAdapter, channelTopic);
//        return container;
//    }
//
////    @Bean
////    public RedisMessageListenerContainer redisMessageListenerContainer(
////            RedisConnectionFactory redisConnectionFactory) {
////        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
////        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
////
////        return redisMessageListenerContainer;
////    }
//
//    /**
//     * 어플리케이션에서 사용할 redisTemplate 설정
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 레디스에 객체를 string 바꾸주는 역활
//        return redisTemplate;
//    }
//    /**
//     * 실제 메시지를 처리하는 subscriber 설정 추가
//     * 모든 메세지를 내가 생성한 RedisSubscriber 클래스가 처리하도록 설정함
//     */
//    @Bean
//    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
//        return new MessageListenerAdapter(subscriber, "sendMessage");
//    }
//    /**
//     * 단일 Topic 사용을 위한 Bean 설정
//     * 사실 중요한 부분인지 모르겠음
//     * 계속해서 생성할 필요가 없어 보여 빈으로 등록함
//     */
//    @Bean
//    public ChannelTopic channelTopic() {
//        return new ChannelTopic("chatroom");
//    }
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(redisHost, redisPort);
//    }
//}