package com.example.hrms.dto;

import com.example.hrms.entity.RoleEnum;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RegisterUserDto {
    private String email;
    private String password;
    private String fullName;
    private List<RoleEnum> roles;  // List of role names (e.g., ["ADMIN", "USER"])
}
