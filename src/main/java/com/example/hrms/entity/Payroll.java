package com.example.hrms.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal salary;
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;
}
