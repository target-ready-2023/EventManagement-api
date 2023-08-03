package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exception.EventNotFoundException;
import com.target.eventmanagementsystem.exception.UserNotFoundException;
import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.EventParticipant;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.repository.EventParticipantRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class EventParticipantService {

    private EventParticipantRepository eventRepository;

    private EventRepository eventRepository1;

    private UserRepository userRepository;

    public EventParticipantService(EventParticipantRepository eventRepository, UserRepository userRepository, EventRepository eventRepository1) {
        this.eventRepository = eventRepository;
        this.eventRepository1 = eventRepository1;
        this.userRepository=userRepository;
    }

    public void registerParticipantForEvent(int eventId, int userId) {
        Optional<Event> eventOptional = eventRepository1.findById(eventId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (eventOptional.isPresent() && userOptional.isPresent()) {
            Event event = eventOptional.get();
            User user = userOptional.get();

            checkLastDate(event);

            eventRepository.save(setEventParticipant(event, user));
        }
        else if(!eventOptional.isPresent()){
            throw new EventNotFoundException("Event not found");
        }
        else if(!userOptional.isPresent()){
            throw new UserNotFoundException("Event not found");
        }
    }



    public void deregisterParticipantFromEvent(Integer eventId, Integer userId) {
        Optional<EventParticipant> eventOptional = eventRepository.findByEventIdAndUserId(eventId, userId);

        if(eventOptional.isPresent()) {
            EventParticipant eventParticipant = eventOptional.get();
            eventRepository.delete(eventParticipant);
        }
        else{
            throw new RuntimeException("Event or participant not found");
        }
    }


    private void checkLastDate(Event event){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date lastDateOfRegistration;

        try {
            lastDateOfRegistration = formatter.parse(event.getLastDateForRegistration());
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
