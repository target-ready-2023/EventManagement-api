package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {

}
