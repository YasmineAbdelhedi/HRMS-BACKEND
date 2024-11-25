package com.example.hrms.service;

import com.example.hrms.entity.LeaveRequest;
import com.example.hrms.entity.LeaveRequest.LeaveStatus;
import com.example.hrms.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Override
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequest.setStatus(LeaveStatus.PENDING);  // Set the default status to PENDING
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest approveLeaveRequest(Long requestId) {
        Optional<LeaveRequest> requestOpt = leaveRequestRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            LeaveRequest request = requestOpt.get();
            request.setStatus(LeaveStatus.APPROVED);
            return leaveRequestRepository.save(request);
        } else {
            throw new RuntimeException("Leave request not found");
        }
    }

    @Override
    public LeaveRequest rejectLeaveRequest(Long requestId) {
        Optional<LeaveRequest> requestOpt = leaveRequestRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            LeaveRequest request = requestOpt.get();
            request.setStatus(LeaveStatus.REJECTED);
            return leaveRequestRepository.save(request);
        } else {
            throw new RuntimeException("Leave request not found");
        }
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByStatus(LeaveStatus status) {
        return leaveRequestRepository.findByStatus(status);
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    @Override
    public Optional<LeaveRequest> findById(Long id) {
        return leaveRequestRepository.findById(id);
    }
}
