package com.example.hrms.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;
}
