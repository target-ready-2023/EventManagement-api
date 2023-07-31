package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.EventParticipants;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.repository.EventParRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public int registerParticipantForEvent(Integer eventId, Integer userId) {
        Optional<Event> eventOptional = eventRepository1.findById(eventId);
        Optional<Users> userOptional = userRepository.findById(userId);

        if (eventOptional.isPresent() && userOptional.isPresent()) {
            Event event = eventOptional.get();
            Users user = userOptional.get();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date lastDateOfRegistration;

            try {
                lastDateOfRegistration = formatter.parse(event.getLast_date_for_registration());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Date currentDate = new Date();

            if(currentDate.after(lastDateOfRegistration)){
                return 1;
            }

            EventParticipantKey key = new EventParticipantKey();
            key.setEventId(eventId);
            key.setUserId(userId);

            EventParticipants eventParticipant = new EventParticipants();
            eventParticipant.setId(key); // Set the composite key
            eventParticipant.setUser(user); // Set the participant
            eventParticipant.setEvent(event); // Set the event
            eventParticipant.setResult(null);

            eventRepository.save(eventParticipant);

            return 0;
        }

        return 2;
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
