package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
