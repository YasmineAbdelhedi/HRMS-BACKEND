package com.example.hrms.repository;


import com.example.hrms.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {
    List<ProjectAssignment> findByProjectId(Long projectId); // Define the method
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
    List<ProjectAssignment> findByUserId(Long userId);

}




