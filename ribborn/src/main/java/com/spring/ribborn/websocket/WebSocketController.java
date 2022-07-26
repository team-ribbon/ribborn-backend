package com.spring.ribborn.websocket;



import com.spring.ribborn.jwt.HeaderTokenExtractor;
import com.spring.ribborn.jwt.JwtAuthenticationFilter;
//import com.spring.ribborn.jwt.JwtDecoder;
import com.spring.ribborn.jwt.JwtTokenProvider;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.websocket.chat.ChatMessageService;
import com.spring.ribborn.websocket.chatDto.MessageRequestDto;
import com.spring.ribborn.websocket.chatDto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatMessageService messageService;
    private final NotificationRepository notificationRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    private final JwtTokenProvider jwtTokenProvider;
    private final HeaderTokenExtractor extractor;


    // 해당 어노테이션을 통해 웹소켓으로 pub 되는 모든 메시지를 처리하게 됩니다. URI에 자동으로 접두어 /pub 이 붙습니다.
//    @PostMapping("/chat/message")
//    public void message(@RequestBody String requestDto,
//                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
//        System.out.println("여긴옴??????????????????????????????????????????????????");
//
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@requestDto" + requestDto);
////        String test = requestDto.getMessage();
////        System.out.println("test = " + test);
//
//        System.out.println("userDetails = " + userDetails.getUser());
//
////        Long idck = userDetails.getUserId();
//            Long idck1 = userDetails.getUser().getId();
////        System.out.println("============================================================================== userDetails.getUserId() = " + idck);
//            System.out.println("===================================================================userDetails.getUser().getId() = " + idck1);
//
//            Long userId = userDetails.getUserId();
//            System.out.println("======================= 메시지 controller  userId = " + userId);

//            MessageResponseDto responseDto = messageService.saveMessage(requestDto, userId); //DB에 저장
//            messageService.sendMessage(requestDto, userId, responseDto); // 메시지를 sub 주소로 발송해줌

//        redisTemplate.convertAndSend(channelTopic.getTopice(), message);
//    }

//    @MessageMapping("/chat/message")
//    public void message(MessageRequestDto requestDto, ServletRequest request) throws IOException { // 토큰을 헤더에서 받는 것으로 최종 확정함
//
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        Long userId = jwtDecoder.decodeTokenByUserId(token);
//
//        MessageResponseDto responseDto = messageService.saveMessage(requestDto, token); //DB에 저장
//        messageService.sendMessage(requestDto, token, responseDto);// 메시지를 sub 주소로 발송해줌
//    }

    // 커넥트와 디스커넥트 시에는 이 주소로 IN과 OUT의 type으로 요청을 보냅니다.
//    @MessageMapping("/chat/connect-status")
//    public void connectStatus(MessageRequestDto requestDto) {
//        messageService.sendStatus(requestDto); // 동시접속자수 검증
//    }

    // 알림 갯수 전달
    @MessageMapping("/notification")
    public void setNotification( @Header("Authorization") String token) throws IOException {

        String jwt = extractor.extract(token);
        String username = jwtTokenProvider.getUserPk(jwt);
        System.out.println(" 알림갯수 전달 username = " + username);

        Map<String, Integer> map = new HashMap<>();
        map.put("NotificationCnt", notificationRepository.
                countNotificationByUserIdAndIsReadIsFalse(username));

        messagingTemplate.convertAndSend("/sub/notification/" + username, map);
    }


    // 해당 어노테이션을 통해 웹소켓으로 pub 되는 모든 메시지를 처리하게 됩니다. URI에 자동으로 접두어 /pub 이 붙습니다.
    @MessageMapping("/chat/message")
    public void message(MessageRequestDto requestDto, @Header("Authorization") String token) throws IOException { // 토큰을 헤더에서 받는 것으로 최종 확정함
        System.out.println("11111111111111111111111111111111 token = " + token);

        String jwt = extractor.extract(token);
        System.out.println("11111111111111111111111111111111111111jwt = " + jwt);
        String username = jwtTokenProvider.getUserPk(jwt);
        String nickname = jwtTokenProvider.getNickName(jwt);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!nickname = " + nickname);

        MessageResponseDto responseDto = messageService.saveMessage(requestDto, username ,  nickname); //DB에 저장
        messageService.sendMessage(requestDto, username, responseDto);// 메시지를 sub 주소로 발송해줌
    }

}