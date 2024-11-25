package com.example.hrms.controller;

import com.example.hrms.entity.Attendance;
import com.example.hrms.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Endpoint to mark check-in time for an employee
    @PostMapping("/checkin/{employeeId}")
    public Attendance markCheckIn(@PathVariable Long employeeId, @RequestParam LocalDateTime checkInTime) {
        return attendanceService.markCheckIn(employeeId, checkInTime);
    }

    // Endpoint to mark check-out time for an employee


    // Get attendance records by employee and date range
    @GetMapping("/employee/{employeeId}/range")
    public List<Attendance> getAttendanceByEmployeeAndDateRange(
            @PathVariable Long employeeId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return attendanceService.getAttendanceByEmployeeAndDateRange(employeeId, startDate, endDate);
    }

    // Get all attendance records
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // Get attendance record by ID
    @GetMapping("/{id}")
    public Attendance getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id).orElseThrow(() -> new RuntimeException("Attendance record not found"));
    }
}
