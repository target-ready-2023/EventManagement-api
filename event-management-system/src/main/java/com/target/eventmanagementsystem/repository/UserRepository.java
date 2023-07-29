package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {

}
