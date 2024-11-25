package com.example.hrms.service;

import com.example.hrms.entity.Payroll;

import java.util.List;
import java.util.Optional;

public interface PayrollService {
    Payroll save(Payroll payroll);
    Optional<Payroll> findById(Long id);
    List<Payroll> findAll();
    void deleteById(Long id);
}
