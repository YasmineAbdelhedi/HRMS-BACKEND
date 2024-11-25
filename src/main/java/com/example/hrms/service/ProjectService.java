package com.example.hrms.service;

import com.example.hrms.dto.CreateProjectDto;
import com.example.hrms.dto.ProjectAssignmentDto;
import com.example.hrms.dto.ProjectDto;
import com.example.hrms.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    ProjectDto createProject(CreateProjectDto createProjectDto); // Create Project

    ProjectDto updateProject(Long projectId, CreateProjectDto createProjectDto); // Update Project

    void deleteProject(Long projectId); // Delete Project

    Optional<Project> findById(Long id); // Find project by ID

    List<ProjectDto> findAll(); // Find all projects

    void assignEmployeeToProject(Long projectId, Long userId, String role); // Assign employees
    void assignEmployeesToProject(Long projectId, List<ProjectAssignmentDto> assignments);

     List<ProjectDto> getProjectsByEmployee(Long employeeId) ;
     List<ProjectDto> getProjectsByProjectManager(Long projectManagerId) ;

    }
