package com.spring.ribborn.websocket;

//import com.spring.ribborn.websocket.chatDto.NotificationDto;
import com.spring.ribborn.exception.CustomException;
import com.spring.ribborn.model.User;
import com.spring.ribborn.sse.SseDto;
import com.spring.ribborn.websocket.chat.ChatRoomRepository;
//import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.websocket.chatDto.NotificationDto;
import lombok.RequiredArgsConstructor;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;

//import static com.spring.ribborn.websocket.chatDto.NotificationType.*;
import java.io.IOException;
import java.util.Map;

import static com.spring.ribborn.exception.ErrorCode.*;
import static io.lettuce.core.RedisURI.DEFAULT_TIMEOUT;

@Service
@RequiredArgsConstructor

public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000;

    private final NotificationRepository notificationRepository;
    private final ChatRoomRepository roomRepository;
    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe(Long userId, String lastEventId) {
        // 1
        String id = userId + "_" + System.currentTimeMillis();
        System.out.println("System.currentTimeMillis()System.currentTimeMillis()System.currentTimeMillis() = " + System.currentTimeMillis());
        System.out.println("serviceserviceserviceserviceserviceserviceserviceserviceserviceserviceserviceserviceserviceservice id = " + id);

        // 2
//        SseEmitter emitter = emitterRepository.save(id, new SseEmitter());
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        SseDto sseDto = new SseDto();
        System.out.println("sseDto = " + sseDto);

        System.out.println("emitteremitteremitteremitteremitteremitteremitteremitteremitteremitteremitteremitteremitteremitteremitter emitter = " + emitter);
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 3
        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]");
        System.out.println(" 더미 데이터 전송 ");
        // 4
        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        // 현재 이쪽 에러

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(userId));
            System.out.println("eventseventseventseventseventseventseventseventseventseventseventseventseventseventseventseventsevents = " + events);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
//                    .forEach(entry -> sendToClient(entry.getKey(), entry.getValue()));
            System.out.println("클라이언트가 미수신 목록이 존재할 경우 재전송");
        }

        System.out.println(" 여긴 마지막 리턴전인데 오니?? " + emitter );
        return emitter ;
    }

//    private void sendToClient( String id, Object data) {
//    @Async

    public void sendToClient(SseEmitter emitter, String id, Object data) {
        System.out.println("센트투클라이언트 진입");
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
            System.out.println("클라이언트에게 전송");
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }




    // 알림 전체 목록
//    public List<NotificationDto> getNotification(UserDetailsImpl userDetails) {
//
//        List<Notification> notifications = notificationRepository.findAllByUserIdOrderByIdDesc(userDetails.getUserId());
//        List<NotificationDto> dtos = new ArrayList<>();
//        for (Notification notification : notifications){
//            switch ( notification.getType() ){
//                case CHAT:
//                    ChatRoom chatRoom = roomRepository.findByIdFetch(notification.getChangeId())
//                            .orElse( null );
//                    if ( chatRoom != null ) {
//                        if ( chatRoom.getAcceptor().getId().equals(userDetails.getUserId()) ) {
//                            dtos.add(NotificationDto.createOf(notification, chatRoom.getRequester()));
//                        } else if ( chatRoom.getRequester().getId().equals(userDetails.getUserId()) ) {
//                            dtos.add(NotificationDto.createOf(notification, chatRoom.getAcceptor()));
//                        }
//                    }
//                    break;
//                case BARTER:
//                    Barter barter = barterRepository.findById(notification.getChangeId())
//                            .orElse(null);
//                    if ( barter != null ) {
//
//                        String[] barterIds = barter.getBarter().split(";");
//                        String[] buyerItemIdList = barterIds[0].split(",");
//
//                        Long[] ids = new Long[buyerItemIdList.length];
//                        for ( int i = 0 ; i < buyerItemIdList.length ; i ++ ){
//                            ids[i] = Long.parseLong(buyerItemIdList[i]);
//                        }
//
//                        List<Item> items = itemRepository.findAllByItemIds(ids);
//                        List<ItemStarDto> starDtos = new ArrayList<>();
//
//                        for ( Item item : items ){
//                            starDtos.add(ItemStarDto.createFrom(item));
//                        }
//                        dtos.add(NotificationDto.createFrom(notification, starDtos));
//                    }
//                    break;
//
//                default: dtos.add(NotificationDto.createFrom(notification)); break;
//            }
//        }
//        return dtos;
//        }

    // 읽음 상태 업데이트
//    @Transactional
//    public void setRead(Long notificationId){
//        Notification notification = notificationRepository
//                .findById(notificationId)
//                .orElseThrow( () -> new CustomException(NOT_FOUND_NOTIFICATION));
//
//        notification.setRead();
//    }

}