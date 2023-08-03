//package com.target.eventManagementSystem.repository;
//
//import com.target.eventManagementSystem.entity.EventParticipantKey;
//import com.target.eventManagementSystem.entity.EventParticipant;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantKey> {
//    Optional<EventParticipant> findByEventIdAndUserId(Integer eventId, Integer userId);
//
//}
