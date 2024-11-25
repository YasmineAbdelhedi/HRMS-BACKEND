package com.example.hrms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String skills;
    private String experience;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private User employee; // Each profile is related to a specific employee

}
