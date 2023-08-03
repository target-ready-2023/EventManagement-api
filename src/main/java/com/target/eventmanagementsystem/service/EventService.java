//package com.target.eventmanagementsystem.service;
//
//import com.target.eventmanagementsystem.repository.EventRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EventService {
//    @Autowired
//    private EventRepository eventRepository;
//
//    public List<Events> listall(){
//        return eventRepository.findAll();
//    }
//
//    public void save(Events events){
//        eventRepository.save(events);
//    }
//
//    public Events get(Integer id){
//        return eventRepository.findById(id).get();
//    }
//
//    public void delete(Integer id){
//        eventRepository.deleteById(id);
//    }
//}


package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.BadRequestException;
import com.target.eventmanagementsystem.exceptions.NotFoundException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with ID: " + id));
    }

    public Event createEvent(Event event) {
        if (event.getTitle() == null || event.getStartDate().isEmpty() ||
                event.getEndDate() == null || event.getLastRegistrationDate().isEmpty()) {
            throw new BadRequestException("Invalid event data. Name and dates must be provided.");
        }

        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        Long eventId = event.getId();
        if (eventId == null || !eventRepository.existsById(eventId)) {
            throw new NotFoundException("Event not found with ID: " + eventId);
        }

        Event existingEvent = eventRepository.findById(eventId).orElse(null);
        if (existingEvent == null) {
            throw new NotFoundException("Event not found with ID: " + eventId);
        }

        if (event.getTitle() == null || event.getTitle().isEmpty() ||
                event.getStartDate() == null || event.getStartDate().isEmpty() ||
                event.getEndDate() == null || event.getEndDate().isEmpty() ||
                event.getLastRegistrationDate() == null || event.getLastRegistrationDate().isEmpty() ) {
            throw new BadRequestException("Invalid event data. Name and dates must be provided.");
        }

        existingEvent.setEventType(event.getEventType());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setTitle(event.getTitle());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setLastRegistrationDate(event.getLastRegistrationDate());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event not found with ID: " + id);
        }

        eventRepository.deleteById(id);
    }
}

