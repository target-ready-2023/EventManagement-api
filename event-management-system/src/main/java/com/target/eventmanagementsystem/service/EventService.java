package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Events;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Events> listall(){
        return eventRepository.findAll();
    }

    public void save(Events events){
        eventRepository.save(events);
    }

    public Events get(Integer id){
        return eventRepository.findById(id).get();
    }

    public void delete(Integer id){
        eventRepository.deleteById(id);
    }
}
