package com.spring.ribborn.sse;

//import com.spring.ribborn.barter.scoreDto.OppentScoreResponseDto;
//import com.spring.ribborn.trade.tradeDto.TradeDecisionDto;
//import com.spring.ribborn.barter.ScoreService;
//import com.spring.ribborn.trade.TradeService;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.websocket.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
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


    // MIME TYPE - text/event-stream 형태로 받아야함.
    // 클라이어트로부터 오는 알림 구독 요청을 받는다.
    // 로그인한 유저는 SSE 연결
    // lAST_EVENT_ID = 이전에 받지 못한 이벤트가 존재하는 경우 [ SSE 시간 만료 혹은 종료 ]
    // 전달받은 마지막 ID 값을 넘겨 그 이후의 데이터[ 받지 못한 데이터 ]부터 받을 수 있게 한다
    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        System.out.println("seseseseseseseseseseseseseseseseseseseiiiiiiiiiiiiiiiiiiiiid = " + id);
        System.out.println("lastEventIdlastEventIdlastEventIdlastEventIdlastEventIdlastEventIdlastEventId = " + lastEventId);
        return notificationService.subscribe(id, lastEventId);
    }

    //알림조회
    @GetMapping(value = "/notifications")
    public List<NotificationDto> findAllNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.findAllNotifications(userDetails.getUser().getId());
    }

    //전체목록 알림 조회에서 해당 목록 클릭 시 읽음처리 ,
    @PostMapping("/notification/read/{notificationId}")
    public void readNotification(@PathVariable Long notificationId){
        notificationService.readNotification(notificationId);
    }
    //알림 조회 - 구독자가 현재 읽지않은 알림 갯수
    @GetMapping(value = "/notifications/count")
    public NotificationCountDto countUnReadNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.countUnReadNotifications(userDetails.getUser().getId());
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
