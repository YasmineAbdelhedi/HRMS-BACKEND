package com.example.hrms.repository;

import com.example.hrms.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // You can add custom queries here if needed
}
