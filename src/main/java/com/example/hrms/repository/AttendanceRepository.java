package com.example.hrms.repository;

import com.example.hrms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Find attendance records by employee and within a specific date and time range
    List<Attendance> findByEmployeeIdAndCheckInTimeBetween(Long employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // Other methods...
}
