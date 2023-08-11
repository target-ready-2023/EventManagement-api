package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.Registration;
import com.target.eventmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByEventIdAndUserId(Long eventId, Long userId);
    Registration findByEventIdAndUserId(Long eventId, Long userId);

    @Query("SELECT e FROM Event e " +
            "INNER JOIN Registration r ON e.id = r.eventId " +
            "WHERE r.userId = :userId")
    List<Event> findAllEventsForUser(@Param("userId") Long userId);

    @Query("SELECT u FROM User u " +
            "JOIN Registration r ON u.id = r.userId " +
            "WHERE r.eventId = :eventId")
    List<User> findAllUsersForEvent(@Param("eventId") Long eventId);

}

