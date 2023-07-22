package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.EventParticipants;
import com.target.eventmanagementsystem.models.Events;
import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.repository.EventParRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventUserService {

    private EventParRepository eventRepository;

    @Autowired
    public EventUserService(EventParRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public boolean registerParticipantForEvent(int eventId, Users participant) {
        Optional<EventParticipants> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            EventParticipants event = eventOptional.get();
            // Additional checks can be added here before registering the participant
            event.setUser(participant);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public boolean deregisterParticipantFromEvent(int eventId, Users participant) {
        Optional<EventParticipants> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            EventParticipants event = eventOptional.get();
            // Additional checks can be added here before deregistering the participant
            if (isUserRegistered(event, participant)) {
                event.setUser(null); // Remove the user from the EventParticipants entity
                eventRepository.save(event);
                return true;
            }
        }
        return false;
    }

    // Check if a given user is registered for an event
    private boolean isUserRegistered(EventParticipants event, Users user) {
        return event.getUser() != null && event.getUser().equals(user);
    }
}
