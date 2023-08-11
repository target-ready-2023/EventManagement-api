package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Registration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EventRegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByEventIdAndUserId(Long eventId, Long userId);
    Registration findByEventIdAndUserId(Long eventId, Long userId);

}

