package com.target.eventManagementSystem.service.Impl;

import com.target.eventManagementSystem.entity.Event;
import com.target.eventManagementSystem.entity.Registration;
import com.target.eventManagementSystem.entity.User;
import com.target.eventManagementSystem.exceptions.ApiException;
import com.target.eventManagementSystem.repository.EventRegistrationRepository;
import com.target.eventManagementSystem.repository.EventRepository;
import com.target.eventManagementSystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventRegistrationService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventRegistrationRepository registrationRepository;

    public EventRegistrationService(EventRepository eventRepository, UserRepository userRepository, EventRegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public void registerUserForEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Event not found with ID: " + eventId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"User not found with ID: " + userId));

        if (isUserAlreadyRegistered(eventId, userId)) {
            throw new ApiException(HttpStatus.NOT_FOUND,"User is already registered for the event.");
        }

        if (!checkLastDate(eventId)){
            throw new ApiException(HttpStatus.BAD_REQUEST,"Registration has been closed as it is beyond last date.");
        }

        Registration registration = new Registration();
        registration.setEventId(eventId);
        registration.setUserId(userId);
        registrationRepository.save(registration);
    }

    @Transactional
    public void deregisterUserFromEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Event not found with ID: " + eventId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"User not found with ID: " + userId));

        Registration registration = registrationRepository.findByEventIdAndUserId(eventId, userId);
        if (registration == null) {
            throw new ApiException(HttpStatus.NOT_FOUND,"User is not registered for the event.");
        }

        registrationRepository.delete(registration);
    }

    private boolean isUserAlreadyRegistered(Long eventId, Long userId) {
        return registrationRepository.existsByEventIdAndUserId(eventId, userId);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersRegisteredForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Event not found with ID: " + eventId));

        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        List<Long> userIds = registrations.stream().map(Registration::getUserId).collect(Collectors.toList());

        return userRepository.findAllById(userIds);
    }

    // Other methods in the service if needed.

    private boolean checkLastDate(Long eventId){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date lastDateOfRegistration;

        try {
            lastDateOfRegistration = formatter.parse(eventRepository.findById(eventId).get().getLastRegistrationDate());
        } catch (ParseException e) {
            throw new RuntimeException("Wrong format of date. Enter in dd-MM-yyyy format");
        }

        Date currentDate = new Date();

        if(currentDate.after(lastDateOfRegistration)){
            return false;
        }
        else{
            return true;
        }
    }
}
