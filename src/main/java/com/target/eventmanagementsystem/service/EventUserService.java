package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.EventParticipants;
import com.target.eventmanagementsystem.models.Events;
import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.repository.EventParRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventUserService {
    @Autowired
    private EventParRepository eventRepository;
    @Autowired
    private EventRepository eventRepository1;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public EventUserService(EventParRepository eventRepository,UserRepository userRepository, EventRepository eventRepository1) {
        this.eventRepository = eventRepository;
        this.eventRepository1 = eventRepository1;
        this.userRepository=userRepository;
    }

    public boolean registerParticipantForEvent(Integer eventId, Integer userId) {
        Optional<Events> eventOptional = eventRepository1.findById(eventId);
        Optional<Users> userOptional = userRepository.findById(userId);

        if (eventOptional.isPresent() && userOptional.isPresent()) {
            Events event = eventOptional.get();
            Users user = userOptional.get();

            EventParticipantKey key = new EventParticipantKey();
            key.setEventId(eventId);
            key.setUserId(userId);

            EventParticipants eventParticipant = new EventParticipants();
            eventParticipant.setId(key); // Set the composite key
            eventParticipant.setUser(user); // Set the participant
            eventParticipant.setEvent(event); // Set the event
            eventParticipant.setResult(null);

            eventRepository.save(eventParticipant);

            return true;
        }

        return false;
    }



    public boolean deregisterParticipantFromEvent(Integer eventId, Integer userId) {
        Optional<EventParticipants> eventOptional = eventRepository.findByEventIdAndUserId(eventId, userId);

        if (eventOptional.isPresent()) {
            EventParticipants event = eventOptional.get();
            eventRepository.delete(event);
            return true;
        }

        return false;
    }




    private boolean isUserRegistered(EventParticipants event, Integer userId) {
        return event.getId().getUserId().equals(userId);
    }

}
