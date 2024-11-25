package com.example.hrms.controller;

import com.example.hrms.entity.LeaveRequest;
import com.example.hrms.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping
    public LeaveRequest createLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.createLeaveRequest(leaveRequest);
    }

    @PutMapping("/approve/{id}")
    public LeaveRequest approveLeaveRequest(@PathVariable Long id) {
        return leaveRequestService.approveLeaveRequest(id);
    }

    @PutMapping("/reject/{id}")
    public LeaveRequest rejectLeaveRequest(@PathVariable Long id) {
        return leaveRequestService.rejectLeaveRequest(id);
    }

    @GetMapping("/status/{status}")
    public List<LeaveRequest> getLeaveRequestsByStatus(@PathVariable LeaveRequest.LeaveStatus status) {
        return leaveRequestService.getLeaveRequestsByStatus(status);
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }
}
