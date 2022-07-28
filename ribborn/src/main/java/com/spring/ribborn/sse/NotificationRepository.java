package com.spring.ribborn.websocket;


import com.spring.ribborn.websocket.chatDto.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationQuerydsl {
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findAllByUserIdOrderByIdDesc(Long userId);

    int countNotificationByUserIdAndIsReadIsFalse(String username);

    void deleteByChangeIdAndType(Long changeId, NotificationType type);

}
