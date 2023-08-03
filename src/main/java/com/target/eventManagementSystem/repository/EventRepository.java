package com.target.eventManagementSystem.repository;

import com.target.eventManagementSystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {

}
