package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.repository.EventParListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventParListService {
    @Autowired
    private EventParListRepository eventParListRepository;


    //Retrieve the details of user registered for a particular event

    public List<Users> getUserRegisteredForEvent(Integer eventId){

        return eventParListRepository.findUsersByEvent(eventId);
    }
}
