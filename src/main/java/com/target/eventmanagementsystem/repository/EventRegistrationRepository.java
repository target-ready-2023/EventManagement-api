package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    Registration findByEventIdAndUserId(Long eventId, Long userId);

    List<Registration> findByEventId(Long eventId);

    List<Registration> findByUserId(Long userId);
}

