package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.EventParticipant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantKey> {
    Optional<EventParticipant> findByEventIdAndUserId(Long eventId, Long userId);

}