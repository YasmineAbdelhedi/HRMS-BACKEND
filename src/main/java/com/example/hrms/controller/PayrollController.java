package com.example.hrms.controller;

import com.example.hrms.entity.Payroll;
import com.example.hrms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping
    public Payroll createPayroll(@RequestBody Payroll payroll) {
        return payrollService.save(payroll);
    }

    @GetMapping("/{id}")
    public Optional<Payroll> getPayrollById(@PathVariable Long id) {
        return payrollService.findById(id);
    }

    @GetMapping
    public List<Payroll> getAllPayrolls() {
        return payrollService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletePayroll(@PathVariable Long id) {
        payrollService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Payroll updatePayroll(@PathVariable Long id, @RequestBody Payroll payroll) {
        payroll.setId(id);
        return payrollService.save(payroll);
    }
}
