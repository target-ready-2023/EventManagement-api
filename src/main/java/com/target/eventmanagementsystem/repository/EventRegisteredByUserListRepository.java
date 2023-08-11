package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.EventParticipant;
import com.target.eventmanagementsystem.models.EventParticipantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegisteredByUserListRepository extends JpaRepository<EventParticipant, EventParticipantKey> {
    @Query("SELECT ep.event FROM EventParticipant ep WHERE ep.user.id = :userId")
    List<Event> findRegisteredEventByUser(Integer userId);

}
