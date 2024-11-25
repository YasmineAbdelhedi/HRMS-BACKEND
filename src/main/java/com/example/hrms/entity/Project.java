package com.example.hrms.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal budget;

    @ManyToOne
    private User projectManager;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Task> tasks;


    @OneToMany(mappedBy = "project")
    private List<ProjectAssignment> projectAssignments;  // This can be used for user assignments
}
