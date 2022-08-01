package com.spring.ribborn.service;

import com.spring.ribborn.dto.responseDto.EventResponseDto;
import com.spring.ribborn.model.Event;
import com.spring.ribborn.repository.EventRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public EventResponseDto findEventUser(UserDetailsImpl userDetails) {
        List<Event> eventUser = eventRepository.findEventUser(userDetails.getUsername());
        if(eventUser.isEmpty()){
            EventResponseDto eventResponseDto = new EventResponseDto();
            eventResponseDto.setParticipation("false");
            return eventResponseDto;
        }else {
            EventResponseDto eventResponseDto = new EventResponseDto();
            eventResponseDto.setParticipation("true");
            return eventResponseDto;
        }
    }

    @Transactional
    public void eventJoin(UserDetailsImpl userDetails) {
        Event event = Event.joinEvent(userDetails.getUsername());
        eventRepository.saveEvent(event);
    }
}
