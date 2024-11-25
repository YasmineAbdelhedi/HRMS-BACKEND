package com.example.hrms.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
    private LeaveStatus status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}