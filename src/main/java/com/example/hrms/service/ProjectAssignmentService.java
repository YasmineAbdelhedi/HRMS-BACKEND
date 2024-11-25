package com.example.hrms.service;

import com.example.hrms.entity.ProjectAssignment;

import java.util.List;
import java.util.Optional;

public interface ProjectAssignmentService {
    ProjectAssignment save(ProjectAssignment projectAssignment);
    Optional<ProjectAssignment> findById(Long id);
    List<ProjectAssignment> findAll();
    void deleteById(Long id);
}
