package com.example.hrms.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @ToString.Exclude  // Exclude employee to prevent circular reference

    private Project project;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @ToString.Exclude  // Exclude employee to prevent circular reference
    private User employee;

    public enum TaskStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
}