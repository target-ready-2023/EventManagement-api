package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.EventParticipants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParRepository extends JpaRepository<EventParticipants, EventParticipantKey> {
}
