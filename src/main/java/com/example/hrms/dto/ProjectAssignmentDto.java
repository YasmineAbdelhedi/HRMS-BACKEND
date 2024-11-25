package com.example.hrms.dto;

import lombok.Data;

@Data
public class ProjectAssignmentDto {
    private Long userId;     // ID of the assigned user
    private String fullName; // Full name of the assigned user
    private String role;     // Role assigned to the user in the project
}
