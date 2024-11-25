package com.example.hrms.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal budget;
    private String projectManagerName;
    private List<String> taskNames;  // Just the names of tasks
    private List<ProjectAssignmentDto> assignments; // Include assignment details
}
