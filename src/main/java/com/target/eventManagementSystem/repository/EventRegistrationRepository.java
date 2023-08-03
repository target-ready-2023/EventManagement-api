package com.target.eventManagementSystem.repository;

import com.target.eventManagementSystem.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    Registration findByEventIdAndUserId(Long eventId, Long userId);

    List<Registration> findByEventId(Long eventId);
}
