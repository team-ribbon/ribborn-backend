package com.spring.ribborn.websocket;

import com.spring.ribborn.websocket.chatDto.NotificationDto;
import com.spring.ribborn.dto.OkDto;
import com.spring.ribborn.websocket.chatDto.MessageResponseDto;
//import com.spring.ribborn.barter.scoreDto.OppentScoreResponseDto;
//import com.spring.ribborn.trade.tradeDto.TradeDecisionDto;
import com.spring.ribborn.security.UserDetailsImpl;
//import com.spring.ribborn.barter.ScoreService;
//import com.spring.ribborn.trade.TradeService;
import com.spring.ribborn.websocket.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class NotificationController {

//    private final ScoreService scoreService;
    private final ChatMessageService messageService;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
//    private final TradeService tradeService;

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        System.out.println("seseseseseseseseseseseseseseseseseseseiiiiiiiiiiiiiiiiiiiiid = " + id);
        System.out.println("lastEventIdlastEventIdlastEventIdlastEventIdlastEventIdlastEventIdlastEventId = " + lastEventId);
        return notificationService.subscribe(id, lastEventId);
    }


    // 알림 전체 목록 가져오기
//    @GetMapping("/api/notifications")
//    public List<NotificationDto> getNotification(@AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        return notificationService.getNotification(userDetails);
//    }

    // 개별 채팅방 메시지 불러오기
//    @GetMapping("/notification/chat")
//    public List<MessageResponseDto> getMessage(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                               @RequestParam Long notificationId,
//                                               @RequestParam Long roomId) {
////                                               @PageableDefault(size = 20) Pageable pageable) {
//
//        Long userid = userDetails.getUserId();
//        String nickname = userDetails.getNickname();
//        List<MessageResponseDto> responseDtos = messageService.getMessage(roomId ,userid, nickname);
//        notificationService.setRead(notificationId);
//        return responseDtos;
//    }


    // 알림 삭제하기
//    @DeleteMapping("/api/notification/{notificationId}")
//    public ResponseEntity<OkDto> deleteNotification(@PathVariable Long notificationId){
//
//        // 회원 비교 절차 필요
//        notificationRepository.deleteById(notificationId);
//        return ResponseEntity.ok().body(OkDto.valueOf("true"));
//    }

    // 회원 가입 메시지
//    @GetMapping("/api/notification/signup") // 회원가입 축하 메시지에 대해 이 주소로 요청을 보내면 작동합니다.
//    public ResponseEntity<OkDto> signup(@RequestParam Long notificationId){
//
//        notificationService.setRead(notificationId);
//        return ResponseEntity.ok().body(OkDto.valueOf("true"));
//    }

}
