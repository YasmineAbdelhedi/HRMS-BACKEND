package com.example.hrms.service;

import com.example.hrms.entity.Attendance;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {

    // Mark check-in for an employee
   Attendance markCheckIn(Long employeeId, LocalDateTime checkInTime);

    // Mark check-out for an employee
   // Attendance markCheckOut(Long employeeId, LocalDateTime checkOutTime);

    // Get attendance records by employee
    //List<Attendance> getAttendanceByEmployee(Long employeeId);

    // Get attendance by date range
    List<Attendance> getAttendanceByEmployeeAndDateRange(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);

    // Get all attendance records
    List<Attendance> getAllAttendance();

    // Get attendance by ID
    Optional<Attendance> getAttendanceById(Long id);
}
