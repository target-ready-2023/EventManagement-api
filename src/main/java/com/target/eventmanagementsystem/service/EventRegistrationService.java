package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.EventParticipant;
import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.User;
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

    private final EventParticipantRepository eventParticipantRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public EventRegistrationService(EventParticipantRepository eventParticipantRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.eventParticipantRepository = eventParticipantRepository;
        this.eventRepository = eventRepository;
        this.userRepository=userRepository;
    }

    public void registerParticipantForEvent(Long eventId, Long userId) {

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Event not found.");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User not found.");
        }

        isRegistrationClosed(eventOptional.get());
        eventParticipantRepository.save(setEventParticipant(eventOptional.get(), userOptional.get()));
    }

    public void deRegisterParticipantFromEvent(Long eventId, Long userId) {

        Optional<EventParticipant> eventParticipant = eventParticipantRepository.findByEventIdAndUserId(eventId, userId);
        if (eventParticipant.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Event or participant not found");
        }

        eventParticipantRepository.delete(eventParticipant.get());
    }


    private void isRegistrationClosed(Event event){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date lastDateOfRegistration;

        try {
            lastDateOfRegistration = formatter.parse(event.getLastRegistrationDate());
        } catch (ParseException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Wrong format of date. The actual error is " + e);
        }

        Date currentDate = new Date();

        if(currentDate.after(lastDateOfRegistration)){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Registration has been closed as it is beyond last date.");
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
