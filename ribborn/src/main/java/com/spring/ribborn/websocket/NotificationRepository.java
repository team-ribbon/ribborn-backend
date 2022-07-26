package com.spring.ribborn.websocket;


import com.spring.ribborn.websocket.chatDto.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationQuerydsl {
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findAllByUserIdOrderByIdDesc(Long userId);

    int countNotificationByUserIdAndIsReadIsFalse(String username);

    void deleteByChangeIdAndType(Long changeId, NotificationType type);

}
