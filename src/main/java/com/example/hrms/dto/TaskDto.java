package com.example.hrms.dto;

import com.example.hrms.entity.Task;
import lombok.Data;

import java.time.LocalDate;
import com.example.hrms.entity.Task.TaskStatus;
@Data
public class TaskDto {

    private Long id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private Long projectId;
    private Long employeeId;

}
