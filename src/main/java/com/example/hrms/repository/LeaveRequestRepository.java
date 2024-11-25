package com.example.hrms.repository;

import com.example.hrms.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    // Find all leave requests with a specific status
    List<LeaveRequest> findByStatus(LeaveRequest.LeaveStatus status);

    // Find all leave requests made by a specific employee
    List<LeaveRequest> findByEmployeeId(Long employeeId);

    // Find all pending leave requests, ordered by creation date
    List<LeaveRequest> findByStatusOrderByStartDateDesc(LeaveRequest.LeaveStatus status);

}
