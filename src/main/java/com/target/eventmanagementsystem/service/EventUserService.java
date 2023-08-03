//package com.target.eventmanagementsystem.service;
//
//import com.target.eventmanagementsystem.models.EventParticipant;
//import com.target.eventmanagementsystem.models.EventParticipantKey;
//import com.target.eventmanagementsystem.repository.EventParticipantRepository;
//import com.target.eventmanagementsystem.repository.EventRepository;
//import com.target.eventmanagementsystem.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class EventUserService {
//    @Autowired
//    private EventParticipantRepository eventRepository;
//    @Autowired
//    private EventRepository eventRepository1;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    public EventUserService(EventParticipantRepository eventRepository, UserRepository userRepository, EventRepository eventRepository1) {
//        this.eventRepository = eventRepository;
//        this.eventRepository1 = eventRepository1;
//        this.userRepository=userRepository;
//    }
//
//    public boolean registerParticipantForEvent(Integer eventId, Integer userId) {
//        Optional<Events> eventOptional = eventRepository1.findById(eventId);
//        Optional<Users> userOptional = userRepository.findById(userId);
//
//        if (eventOptional.isPresent() && userOptional.isPresent()) {
//            Events event = eventOptional.get();
//            Users user = userOptional.get();
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
//            eventRepository.save(eventParticipant);
//
//            return true;
//        }
//
//        return false;
//    }
//
//
//
//    public boolean deregisterParticipantFromEvent(Integer eventId, Integer userId) {
//        Optional<EventParticipant> eventOptional = eventRepository.findByEventIdAndUserId(eventId, userId);
//
//        if (eventOptional.isPresent()) {
//            EventParticipant event = eventOptional.get();
//            eventRepository.delete(event);
//            return true;
//        }
//
//        return false;
//    }
//
//
//
//
//    private boolean isUserRegistered(EventParticipant event, Integer userId) {
//        return event.getId().getUserId().equals(userId);
//    }
//
//}

package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.EventParticipant;
import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.repository.EventParticipantRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventUserService {
    @Autowired
    private EventParticipantRepository eventParticipantRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public EventUserService(EventParticipantRepository eventParticipantRepository,UserRepository userRepository, EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.eventParticipantRepository = eventParticipantRepository;
        this.userRepository=userRepository;
    }

    public boolean registerParticipantForEvent(Long eventId, Long userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (eventOptional.isPresent() && userOptional.isPresent()) {
            Event event = eventOptional.get();
            User user = userOptional.get();

            EventParticipantKey key = new EventParticipantKey();
            key.setEventId(eventId);
            key.setUserId(userId);

            EventParticipant eventParticipant = new EventParticipant();
            eventParticipant.setId(key); // Set the composite key
            eventParticipant.setUser(user); // Set the participant
            eventParticipant.setEvent(event); // Set the event
            eventParticipant.setResult(null);

            eventRepository.save(eventParticipant);

            return true;
        }

        return false;
    }



    public boolean deregisterParticipantFromEvent(Long eventId, Long userId) {
        Optional<EventParticipant> eventOptional = eventParticipantRepository.findByEventIdAndUserId(eventId, userId);

        if (eventOptional.isPresent()) {
            EventParticipant event = eventOptional.get();
            eventRepository.delete(event.getEvent());
            return true;
        }

        return false;
    }


    private boolean isUserRegistered(EventParticipant event, Long userId) {
        return event.getId().getUserId().equals(userId);
    }

}
