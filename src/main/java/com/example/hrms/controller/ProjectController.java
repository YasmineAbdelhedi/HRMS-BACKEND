package com.example.hrms.controller;

import com.example.hrms.dto.CreateProjectDto;
import com.example.hrms.dto.ProjectAssignmentDto;
import com.example.hrms.dto.ProjectDto;
import com.example.hrms.entity.Project;
import com.example.hrms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(@RequestBody CreateProjectDto createProjectDto) {
        ProjectDto projectDto = projectService.createProject(createProjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @RequestBody CreateProjectDto createProjectDto) {
        try {
            ProjectDto updatedProject = projectService.updateProject(id, createProjectDto);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{projectId}/assign")
    public ResponseEntity<String> assignEmployeeToProject(
            @PathVariable Long projectId,
            @RequestParam Long userId,
            @RequestParam String role) {
        try {
            projectService.assignEmployeeToProject(projectId, userId, role);
            return ResponseEntity.ok("User assigned to project successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning user to project.");
        }
    }

    @PostMapping("/{projectId}/assign-employees")
    public ResponseEntity<String> assignEmployeesToProject(
            @PathVariable Long projectId,
            @RequestBody List<ProjectAssignmentDto> assignments) {
        try {
            projectService.assignEmployeesToProject(projectId, assignments);
            return ResponseEntity.ok("Employees successfully assigned to the project.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning user to project.");
        }


    }

    @GetMapping("/GetProjectsByPM/{managerId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByManager(@PathVariable Long managerId) {
        List<ProjectDto> projects = projectService.getProjectsByProjectManager(managerId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/GetProjectsByEmployee/{employeeId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByEmployee(@PathVariable Long employeeId) {
        List<ProjectDto> projects = projectService.getProjectsByEmployee(employeeId);
        return ResponseEntity.ok(projects);
    }

}
