package com.spring.ribborn.controller;

import com.spring.ribborn.dto.responseDto.EventResponseDto;
import com.spring.ribborn.security.UserDetailsImpl;
import com.spring.ribborn.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    //이벤트 참여
    @PostMapping("/api/event/participation")
    public void eventJoin(@AuthenticationPrincipal UserDetailsImpl userDetails){
        eventService.eventJoin(userDetails);
    }

    //이벤트 페이지 조회
    @GetMapping("/api/event/1")
    public EventResponseDto eventPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails != null){
            EventResponseDto eventUser = eventService.findEventUser(userDetails);
            return eventUser;
        }
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setParticipation("false");
        return eventResponseDto;
    }
}
