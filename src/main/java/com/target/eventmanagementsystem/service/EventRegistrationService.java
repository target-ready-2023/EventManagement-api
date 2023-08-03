package com.target.eventmanagementsystem.service;

//import com.target.eventmanagementsystem.models.Event;
//import com.target.eventmanagementsystem.models.EventParticipant;
//import com.target.eventmanagementsystem.models.EventParticipantKey;
//import com.target.eventmanagementsystem.models.User;
//import com.target.eventmanagementsystem.repository.EventParticipantRepository;
//import com.target.eventmanagementsystem.repository.EventRepository;
//import com.target.eventmanagementsystem.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class EventRegistrationService {
//    @Autowired
//    private EventParticipantRepository eventParticipantRepository;
//    @Autowired
//    private EventRepository eventRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    public EventRegistrationService(EventParticipantRepository eventParticipantRepository, UserRepository userRepository, EventRepository eventRepository) {
//        this.eventRepository = eventRepository;
//        this.eventParticipantRepository = eventParticipantRepository;
//        this.userRepository=userRepository;
//    }
//
//    public boolean registerParticipantForEvent(Long eventId, Long userId) {
//        Optional<Event> eventOptional = eventRepository.findById(eventId);
//        Optional<User> userOptional = userRepository.findById(userId);
//
//        if (eventOptional.isPresent() && userOptional.isPresent()) {
//            Event event = eventOptional.get();
//            User user = userOptional.get();
//
//            EventParticipantKey key = new EventParticipantKey();
//            key.setEventId(eventId);
//            key.setUserId(userId);
//
//            EventParticipant eventParticipant = new EventParticipant();
//            eventParticipant.setId(key); // Set the composite key
//            eventParticipant.setUser(user); // Set the participant
//            eventParticipant.setEvent(event); // Set the event
//            eventParticipant.setResult(null);
//
//            eventParticipantRepository.save(eventParticipant);
//
//            return true;
//        }
//
//        return false;
//    }
//
//
//
//    public boolean deregisterParticipantFromEvent(Long eventId, Long userId) {
//        Optional<EventParticipant> eventOptional = eventParticipantRepository.findByEventIdAndUserId(eventId, userId);
//
//        if (eventOptional.isPresent()) {
//            EventParticipant event = eventOptional.get();
//            eventRepository.delete(event.getEvent());
//            return true;
//        }
//
//        return false;
//    }
//
//
//    private boolean isUserRegistered(EventParticipant event, Long userId) {
//        return event.getId().getUserId().equals(userId);
//    }
//
//}

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.EventParticipant;
import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.repository.EventParticipantRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
