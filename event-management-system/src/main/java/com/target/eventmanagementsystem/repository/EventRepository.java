package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EventRepository extends JpaRepository<Event,Long> {

}
