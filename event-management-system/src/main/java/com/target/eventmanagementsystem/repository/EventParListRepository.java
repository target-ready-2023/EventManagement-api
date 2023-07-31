package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.EventParticipantKey;
import com.target.eventmanagementsystem.models.EventParticipants;
import com.target.eventmanagementsystem.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventParListRepository extends CrudRepository<EventParticipants, EventParticipantKey> {
    @Query("SELECT ep.user FROM EventParticipants ep WHERE ep.event.id = :eventId")
    List<Users> findUsersByEvent(Integer eventId);
}
