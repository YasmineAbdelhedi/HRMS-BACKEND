package com.example.hrms.service;

        import com.example.hrms.entity.LeaveRequest;
        import java.util.List;
        import java.util.Optional;

public interface LeaveRequestService {

    LeaveRequest createLeaveRequest(LeaveRequest leaveRequest);

    LeaveRequest approveLeaveRequest(Long requestId);

    LeaveRequest rejectLeaveRequest(Long requestId);

    List<LeaveRequest> getLeaveRequestsByStatus(LeaveRequest.LeaveStatus status);

    List<LeaveRequest> getAllLeaveRequests();

    Optional<LeaveRequest> findById(Long id);
}
