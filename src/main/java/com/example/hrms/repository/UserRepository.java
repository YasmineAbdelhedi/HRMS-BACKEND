package com.example.hrms.repository;

import com.example.hrms.entity.ProjectAssignment;
import com.example.hrms.entity.RoleEnum;
import com.example.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // Find user by username (for authentication purposes)

    // Optionally, find user by email (if you plan on using email for login)
    Optional<User> findByEmail(String email);

    List<User> findByRoles_Name(RoleEnum role);  // Find users by role
}
