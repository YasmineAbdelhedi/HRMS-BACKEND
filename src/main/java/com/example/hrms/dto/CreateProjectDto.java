package com.example.hrms.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProjectDto {
    private String name;
    private String description;
    private BigDecimal budget;
}
