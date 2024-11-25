package com.example.hrms.service;

import com.example.hrms.dto.CreateProjectDto;
import com.example.hrms.dto.ProjectAssignmentDto;
import com.example.hrms.dto.ProjectDto;
import com.example.hrms.entity.Project;
import com.example.hrms.entity.ProjectAssignment;
import com.example.hrms.entity.User;
import com.example.hrms.repository.ProjectAssignmentRepository;
import com.example.hrms.repository.ProjectRepository;
import com.example.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectAssignmentRepository projectAssignmentRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ProjectAssignmentRepository projectAssignmentRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectAssignmentRepository = projectAssignmentRepository;
    }

    @Override
    public ProjectDto createProject(CreateProjectDto createProjectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!hasPermission(currentUser, "CREATE_PROJECT")) {
            throw new AccessDeniedException("You do not have permission to create a project.");
        }

        Project project = new Project();
        project.setName(createProjectDto.getName());
        project.setDescription(createProjectDto.getDescription());
        project.setBudget(createProjectDto.getBudget());
        project.setProjectManager(currentUser);

        return mapToProjectDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto updateProject(Long projectId, CreateProjectDto createProjectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!hasPermission(currentUser, "UPDATE_PROJECT")) {
            throw new AccessDeniedException("You do not have permission to update this project.");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(createProjectDto.getName());
        project.setDescription(createProjectDto.getDescription());
        project.setBudget(createProjectDto.getBudget());

        return mapToProjectDto(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!hasPermission(currentUser, "DELETE_PROJECT")) {
            throw new AccessDeniedException("You do not have permission to delete this project.");
        }

        projectRepository.deleteById(projectId);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream()
                .map(this::mapToProjectDto)
                .collect(Collectors.toList());
    }

    @Override
    public void assignEmployeeToProject(Long projectId, Long userId, String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!hasPermission(currentUser, "ASSIGN_EMPLOYEES_TO_PROJECT")) {
            throw new AccessDeniedException("You do not have permission to assign employees to projects.");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user already has the EMPLOYEE role assigned to this project
        boolean isAlreadyAssigned = projectAssignmentRepository.existsByProjectIdAndUserId(projectId, userId);
        if (isAlreadyAssigned) {
            throw new RuntimeException("This user is already assigned to the project.");
        }



        // Create and save the project assignment
        ProjectAssignment projectAssignment = new ProjectAssignment();
        projectAssignment.setProject(project);
        projectAssignment.setUser(user);
        projectAssignment.setRole(role);

        projectAssignmentRepository.save(projectAssignment);
    }

    @Override
    public void assignEmployeesToProject(Long projectId, List<ProjectAssignmentDto> assignments) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Permission check
        if (!hasPermission(currentUser, "ASSIGN_EMPLOYEES_TO_PROJECT")) {
            throw new AccessDeniedException("You do not have permission to assign employees to projects.");
        }

        // Check if project exists
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        for (ProjectAssignmentDto assignment : assignments) {
            User user = userRepository.findById(assignment.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with ID " + assignment.getUserId() + " not found"));

            // Ensure user is not already assigned
            boolean isAlreadyAssigned = projectAssignmentRepository.existsByProjectIdAndUserId(projectId, user.getId());
            if (isAlreadyAssigned) {
                throw new RuntimeException("User with ID " + user.getId() + " is already assigned to the project.");
            }


            // Create and save the assignment
            ProjectAssignment projectAssignment = new ProjectAssignment();
            projectAssignment.setProject(project);
            projectAssignment.setUser(user);
            projectAssignment.setRole(assignment.getRole());

            projectAssignmentRepository.save(projectAssignment);
        }
    }


    private boolean hasPermission(User user, String permission) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(permission));
    }

    // Mapper methods
    private ProjectDto mapToProjectDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setBudget(project.getBudget());
        projectDto.setProjectManagerName(project.getProjectManager().getFullName());

        projectDto.setTaskNames(
                project.getTasks().stream()
                        .map(task -> task.getName())
                        .collect(Collectors.toList())
        );

        projectDto.setAssignments(
                projectAssignmentRepository.findByProjectId(project.getId()).stream()
                        .map(this::mapToProjectAssignmentDto)
                        .collect(Collectors.toList())
        );

        return projectDto;
    }

    private ProjectAssignmentDto mapToProjectAssignmentDto(ProjectAssignment assignment) {
        ProjectAssignmentDto assignmentDto = new ProjectAssignmentDto();
        assignmentDto.setUserId(assignment.getUser().getId());
        assignmentDto.setFullName(assignment.getUser().getFullName());
        assignmentDto.setRole(assignment.getRole());
        return assignmentDto;
    }

    @Override
    public List<ProjectDto> getProjectsByProjectManager(Long projectManagerId) {
        // Find all projects managed by the project manager
        List<Project> projects = projectRepository.findByProjectManagerId(projectManagerId);
        return projects.stream().map(this::mapToProjectDto).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> getProjectsByEmployee(Long employeeId) {
        // Find all project assignments for the employee
        List<ProjectAssignment> assignments = projectAssignmentRepository.findByUserId(employeeId);

        // Extract projects from the assignments
        List<Project> projects = assignments.stream()
                .map(ProjectAssignment::getProject)
                .distinct()
                .collect(Collectors.toList());

        return projects.stream().map(this::mapToProjectDto).collect(Collectors.toList());
    }
}
