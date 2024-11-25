package com.example.hrms.service;

import com.example.hrms.entity.Payroll;
import com.example.hrms.repository.PayrollRepository;
import com.example.hrms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Override
    public Payroll save(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    @Override
    public Optional<Payroll> findById(Long id) {
        return payrollRepository.findById(id);
    }

    @Override
    public List<Payroll> findAll() {
        return payrollRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        payrollRepository.deleteById(id);
    }
}
