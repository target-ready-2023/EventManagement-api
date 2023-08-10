package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.models.Registration;
import com.target.eventmanagementsystem.repository.EventRegistrationRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

        if (!user.getRole().equalsIgnoreCase("Student")) {
            throw new ApiException(HttpStatus.NOT_FOUND,"Only students are allowed to register for events.");
        }

        LocalDate currentDate = LocalDate.now();
        if (event.getLastRegistrationDate().isBefore(currentDate)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,"Registration for the event is already closed.");
        }

        if (event.getStartDate().isBefore(currentDate)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,"Event has already started. Registration is closed.");
        }

        Registration registration = new Registration();
        registration.setEventId(eventId);
        registration.setUserId(userId);
        registrationRepository.save(registration);
    }

    @Transactional
    public void deregisterUserFromEvent(Long eventId, Long userId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Event not found with ID: " + eventId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        Registration registration = registrationRepository.findByEventIdAndUserId(eventId, userId);

        if (registration == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User is not registered for the event.");
        }

        LocalDate currentDate = LocalDate.now();
        if (event.getStartDate().isBefore(currentDate)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Event has already started. Deregistration is not allowed.");
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

    @Transactional(readOnly = true)
    public List<Event> getAllEventsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"User not found with ID: " + userId));

        List<Registration> registrations = registrationRepository.findByUserId(userId);
        List<Long> eventIds = registrations.stream().map(Registration::getEventId).collect(Collectors.toList());
        return eventRepository.findAllById(eventIds);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsersForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Event not found with ID: " + eventId));

        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        List<Long> userIds = registrations.stream().map(Registration::getUserId).collect(Collectors.toList());
        return userRepository.findAllById(userIds);
    }

}
