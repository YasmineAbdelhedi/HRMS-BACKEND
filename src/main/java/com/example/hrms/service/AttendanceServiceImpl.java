package com.example.hrms.service;

import com.example.hrms.entity.Attendance;
import com.example.hrms.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance markCheckIn(Long employeeId, LocalDateTime checkInTime) {
        Attendance attendance = new Attendance();
        //attendance.setEmployeeId(employeeId);
        attendance.setCheckInTime(checkInTime);
        return attendanceRepository.save(attendance);
    }




    @Override
    public List<Attendance> getAttendanceByEmployeeAndDateRange(Long employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        return attendanceRepository.findByEmployeeIdAndCheckInTimeBetween(employeeId, startDate, endDate);
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }
}
