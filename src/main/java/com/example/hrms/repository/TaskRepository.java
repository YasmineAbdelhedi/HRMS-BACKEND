package com.example.hrms.repository;

import com.example.hrms.entity.Task;
import com.example.hrms.entity.User;
import com.example.hrms.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEmployee(User employee); // Find tasks assigned to a specific user (employee)
    List<Task> findByProject(Project project); // Find tasks for a specific project
    List<Task> findByProjectId(Long projectId);
    List<Task> findByEmployeeId(Long employeeId);
}

