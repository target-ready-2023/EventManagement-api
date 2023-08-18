package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.models.Registration;
import com.target.eventmanagementsystem.models.UserRoles;
import com.target.eventmanagementsystem.repository.EventRegistrationRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventRegistrationService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventRegistrationRepository registrationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public EventRegistrationService(EventRepository eventRepository, UserRepository userRepository, EventRegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
    }

    public void registerUserForEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Event not found with ID: " + eventId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"User not found with ID: " + userId));

        if (isUserAlreadyRegistered(eventId, userId)) {
            throw new ApiException(HttpStatus.NOT_FOUND,"User is already registered for the event.");
        }


        if (!UserRoles.STUDENT.equals(user.getRole())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Only students are allowed to register for events.");
        }

        LocalDate currentDate = LocalDate.now();
        if (event.getLastRegistrationDate().isBefore(currentDate)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,"Registration for the event is already closed.");
        }

        Registration registration = new Registration();
        registration.setEventId(eventId);
        registration.setUserId(userId);
        registrationRepository.save(registration);
    }

    public void deregisterUserFromEvent(Long eventId, Long userId) {

        Registration registration = registrationRepository.findByEventIdAndUserId(eventId, userId);

        if (registration == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User is not registered for the event.");
        }

        registrationRepository.delete(registration);
    }

    public boolean isUserAlreadyRegistered(Long eventId, Long userId) {
        return registrationRepository.existsByEventIdAndUserId(eventId, userId);
    }

    public List<Event> getAllEventsForUser(Long userId) {
        return registrationRepository.findAllEventsForUser(userId);
    }

    public List<User> getAllUsersForEvent(Long eventId) {
        return registrationRepository.findAllUsersForEvent(eventId);
    }

}
