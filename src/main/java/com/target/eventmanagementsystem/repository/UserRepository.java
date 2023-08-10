package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByEmail(String email);
}
