package com.example.hrms.service;

import com.example.hrms.dto.TaskDto;
import com.example.hrms.entity.Task;
import com.example.hrms.entity.User;
import com.example.hrms.entity.Project;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskDto save(TaskDto taskDto);
    TaskDto findById(Long id);
    List<TaskDto> findByEmployee(User employee);
    List<TaskDto> findByProject(Project project);
    TaskDto updateStatus(Long taskId, Task.TaskStatus status);
    void deleteById(Long id);
    TaskDto createAndAssignTask(Long projectId, Long employeeId, String name, String description, LocalDate dueDate);
    List<TaskDto> getAllTasks();
}
