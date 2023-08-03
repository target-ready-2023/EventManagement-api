package com.target.eventmanagementsystem.service.Impl;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.entity.Event;
import com.target.eventmanagementsystem.entity.EventParticipant;
import com.target.eventmanagementsystem.entity.EventParticipantKey;
import com.target.eventmanagementsystem.entity.User;
import com.target.eventmanagementsystem.repository.EventParticipantRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class EventRegistrationService {

    private EventParticipantRepository eventParticipantRepository;

    private EventRepository eventRepository;

    private UserRepository userRepository;

    public EventRegistrationService(EventParticipantRepository eventParticipantRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.eventParticipantRepository = eventParticipantRepository;
        this.eventRepository = eventRepository;
        this.userRepository=userRepository;
    }

    public void registerParticipantForEvent(Long eventId, Long userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (eventOptional.isPresent() && userOptional.isPresent()) {
            Event event = eventOptional.get();
            User user = userOptional.get();

            checkLastDate(event);

            eventParticipantRepository.save(setEventParticipant(event, user));
        }
        else if(!eventOptional.isPresent()){
            throw new ApiException(HttpStatus.NOT_FOUND, "Event not found.");
        }
        else if(!userOptional.isPresent()){
            throw new ApiException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }



    public void deregisterParticipantFromEvent(Long eventId, Long userId) {
        Optional<EventParticipant> eventOptional = eventParticipantRepository.findByEventIdAndUserId(eventId, userId);

        if(eventOptional.isPresent()) {
            EventParticipant eventParticipant = eventOptional.get();
            eventParticipantRepository.delete(eventParticipant);
        }
        else{
            throw new RuntimeException("Event or participant not found");
        }
    }


    private void checkLastDate(Event event){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date lastDateOfRegistration;

        try {
            lastDateOfRegistration = formatter.parse(event.getLastRegistrationDate());
        } catch (ParseException e) {
            throw new RuntimeException("Wrong format of date. The actual error is " + e);
        }

        Date currentDate = new Date();

        if(currentDate.after(lastDateOfRegistration)){
            throw new RuntimeException("Registration has been closed as it is beyond last date.");
        }
    }

    private EventParticipant setEventParticipant(Event event, User user){
        EventParticipantKey key = new EventParticipantKey();
        key.setEventId(event.getId());
        key.setUserId(user.getId());

        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setId(key); // Set the composite key
        eventParticipant.setUser(user); // Set the participant
        eventParticipant.setEvent(event); // Set the event
        eventParticipant.setResult(0);

        return eventParticipant;
    }
}
