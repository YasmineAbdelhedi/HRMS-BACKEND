package com.example.hrms.service;

import com.example.hrms.entity.ProjectAssignment;
import com.example.hrms.repository.ProjectAssignmentRepository;
import com.example.hrms.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService {

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;

    @Override
    public ProjectAssignment save(ProjectAssignment projectAssignment) {
        return projectAssignmentRepository.save(projectAssignment);
    }

    @Override
    public Optional<ProjectAssignment> findById(Long id) {
        return projectAssignmentRepository.findById(id);
    }

    @Override
    public List<ProjectAssignment> findAll() {
        return projectAssignmentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        projectAssignmentRepository.deleteById(id);
    }
}
