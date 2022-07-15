package com.spring.ribborn.websocket.chat;

import com.spring.ribborn.websocket.ChatFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatFilterRepository extends JpaRepository<ChatFilter, Long> {
}