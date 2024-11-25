package com.example.hrms.controller;

import com.example.hrms.dto.TaskDto;
import com.example.hrms.entity.Task.TaskStatus;
import com.example.hrms.entity.Project;
import com.example.hrms.entity.User;
import com.example.hrms.service.TaskService;
import com.example.hrms.service.ProjectService;
import com.example.hrms.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    // Create and assign a task
    @PostMapping("/create")
    public ResponseEntity<TaskDto> createAndAssignTask(
            @RequestParam Long projectId,
            @RequestParam Long employeeId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {

        TaskDto taskDto = taskService.createAndAssignTask(projectId, employeeId, name, description, dueDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    // Get all tasks
    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Get all tasks for the authenticated employee
    @GetMapping("/my-tasks")
    public ResponseEntity<List<TaskDto>> getMyTasks() {
        User user = getAuthenticatedUser();
        List<TaskDto> tasks = taskService.findByEmployee(user);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks by project
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDto>> getTasksByProject(@PathVariable Long projectId) {
        User user = getAuthenticatedUser();

        // Fetch the project entity
        Project project = projectService.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        List<TaskDto> tasks = taskService.findByProject(project);
        return ResponseEntity.ok(tasks);
    }

    // Update the status of a task
    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long taskId, @RequestBody TaskStatus status) {
        User user = getAuthenticatedUser();

        // Fetch the task and ensure it is assigned to the logged-in user
        TaskDto task = taskService.findById(taskId);


        // Update and return the task with the new status
        TaskDto updatedTask = taskService.updateStatus(taskId, status);
        return ResponseEntity.ok(updatedTask);
    }

    // Delete a task (only Admin or Project Manager)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        User user = getAuthenticatedUser();

        // Fetch the TaskDto
        TaskDto task = taskService.findById(id);

        // Fetch the Project entity associated with the task
        Project project = projectService.findById(task.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        // Ensure that only the project manager or admin can delete a task
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        boolean isProjectManager = project.getProjectManager().equals(user);

        if (isAdmin || isProjectManager) {
            taskService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new AccessDeniedException("Only Project Managers or Admin can delete this task.");
        }
    }


    // Helper method to get the authenticated user
    private User getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }
}
