package com.spring.ribborn.websocket;



import com.spring.ribborn.jwt.HeaderTokenExtractor;
//import com.spring.ribborn.jwt.JwtDecoder;
import com.spring.ribborn.jwt.JwtTokenProvider;
import com.spring.ribborn.sse.NotificationRepository;
import com.spring.ribborn.websocket.chat.ChatMessageService;
import com.spring.ribborn.websocket.chatDto.MessageRequestDto;
import com.spring.ribborn.websocket.chatDto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatMessageService messageService;
    private final NotificationRepository notificationRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    private final JwtTokenProvider jwtTokenProvider;
    private final HeaderTokenExtractor extractor;

    // 커넥트와 디스커넥트 시에는 이 주소로 IN과 OUT의 type으로 요청을 보냅니다.
//    @MessageMapping("/chat/connect-status")
//    public void connectStatus(MessageRequestDto requestDto) {
//        messageService.sendStatus(requestDto); // 동시접속자수 검증
//    }

    // 알림 갯수 전달
//    @MessageMapping("/notification")
//    public void setNotification( @Header("Authorization") String token) throws IOException {
//
//        String jwt = extractor.extract(token);
//        String username = jwtTokenProvider.getUserPk(jwt);
//        System.out.println(" 알림갯수 전달 username = " + username);
//
//        Map<String, Integer> map = new HashMap<>();
//        map.put("NotificationCnt", notificationRepository.
//                countNotificationByUserIdAndIsReadIsFalse(username));
//
//        messagingTemplate.convertAndSend("/sub/notification/" + username, map);
//    }



    // 해당 어노테이션을 통해 웹소켓으로 pub 되는 모든 메시지를 처리하게 됩니다. URI에 자동으로 접두어 /pub 이 붙습니다.
    @MessageMapping("/chat/message")
    public void message(MessageRequestDto requestDto, @Header("Authorization") String token) throws IOException { // 토큰을 헤더에서 받는 것으로 최종 확정함

        String jwt = extractor.extract(token);
        String username = jwtTokenProvider.getUserPk(jwt);
        String nickname = jwtTokenProvider.getNickName(jwt);
        Long userId = jwtTokenProvider.getUserId(jwt);

        MessageResponseDto responseDto = messageService.saveMessage(requestDto, username ,  nickname); //DB에 저장
        messageService.sendMessage(requestDto, username, responseDto);// 메시지를 sub 주소로 발송해줌
    }

    @MessageMapping("/chat/message/img")
//    public void uploadMessageImg(@RequestPart("image") File image, MessageRequestDto requestDto, @Header("Authorization") String token) throws IOException {
    public void uploadMessageImg(@RequestPart(value = "file", required = false) @Nullable MultipartFile image, MessageRequestDto requestDto, @Header("Authorization") String token) throws IOException {

        //            token = token.substring(7);
        String jwt = extractor.extract(token);
        String username = jwtTokenProvider.getUserPk(jwt);
        String nickname = jwtTokenProvider.getNickName(jwt);
        Long userId = jwtTokenProvider.getUserId(jwt);

            messageService.uploadChatMessageImg(image, requestDto);
//            messageService.uploadChatMessageImg(image, requestDto);

    }

}