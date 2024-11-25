package com.example.hrms.repository;

import com.example.hrms.entity.Role;
import com.example.hrms.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);  // Find Role by RoleEnum
}
