package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.EventParticipant;
import com.target.eventmanagementsystem.models.EventParticipantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantKey> {
    Optional<EventParticipant> findByEventIdAndUserId(Long eventId, Long userId);

    @Query("SELECT ep.event FROM EventParticipant ep WHERE ep.user.id = :userId")
    List<Event> findRegisteredEventByUser(Integer userId);

}