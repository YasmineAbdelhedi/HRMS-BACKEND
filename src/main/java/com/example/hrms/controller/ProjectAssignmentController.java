package com.example.hrms.controller;

import com.example.hrms.entity.ProjectAssignment;
import com.example.hrms.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project-assignments")
public class ProjectAssignmentController {

    @Autowired
    private ProjectAssignmentService projectAssignmentService;

    @PostMapping
    public ProjectAssignment createProjectAssignment(@RequestBody ProjectAssignment projectAssignment) {
        return projectAssignmentService.save(projectAssignment);
    }

    @GetMapping("/{id}")
    public Optional<ProjectAssignment> getProjectAssignmentById(@PathVariable Long id) {
        return projectAssignmentService.findById(id);
    }

    @GetMapping
    public List<ProjectAssignment> getAllProjectAssignments() {
        return projectAssignmentService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProjectAssignment(@PathVariable Long id) {
        projectAssignmentService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProjectAssignment updateProjectAssignment(@PathVariable Long id, @RequestBody ProjectAssignment projectAssignment) {
        projectAssignment.setId(id);
        return projectAssignmentService.save(projectAssignment);
    }
}
