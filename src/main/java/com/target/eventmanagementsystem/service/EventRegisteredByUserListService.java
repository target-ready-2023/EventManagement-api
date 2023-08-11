package com.target.eventmanagementsystem.service;


import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.repository.EventParticipantRepository;
import com.target.eventmanagementsystem.repository.EventRegisteredByUserListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventRegisteredByUserListService {
    private final EventParticipantRepository eventRegisteredByUserListRepository;

    @Autowired
    public EventRegisteredByUserListService(EventParticipantRepository eventRegisteredByUserListRepository){
        this.eventRegisteredByUserListRepository = eventRegisteredByUserListRepository;
    }


    public List<Event> getEventsRegisteredByUser(Integer userId){
        List<Event> registeredEvents;
        try{
            registeredEvents = eventRegisteredByUserListRepository.findRegisteredEventByUser(userId);
        }catch (NoSuchElementException e){
            registeredEvents = Collections.emptyList();
        }

        return registeredEvents;
    }
}
