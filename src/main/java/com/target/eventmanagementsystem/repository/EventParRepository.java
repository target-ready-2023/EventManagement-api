package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.EventParticipants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventParRepository extends JpaRepository<EventParticipants, EventParticipantKey> {
    Optional<EventParticipants> findByEventIdAndUserId(Integer eventId, Integer userId);

}
