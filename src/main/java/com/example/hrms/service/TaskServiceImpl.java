package com.example.hrms.service;

import com.example.hrms.dto.TaskDto;
import com.example.hrms.entity.Task;
import com.example.hrms.entity.User;
import com.example.hrms.entity.Project;
import com.example.hrms.repository.TaskRepository;
import com.example.hrms.repository.ProjectRepository;
import com.example.hrms.repository.ProjectAssignmentRepository;
import com.example.hrms.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;


    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectAssignmentRepository projectAssignmentRepository, ProjectRepository projectRepository, UserRepository userRepository,JavaMailSender mailSender) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectAssignmentRepository = projectAssignmentRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;

    }

    @Override
    public TaskDto save(TaskDto taskDto) {
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setStatus(taskDto.getStatus());

        // Fetch the related entities: Project and Employee
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User employee = userRepository.findById(taskDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        task.setProject(project);
        task.setEmployee(employee);

        Task savedTask = taskRepository.save(task);
        return mapToTaskDto(savedTask);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found for ID: " + id));
        return mapToTaskDto(task);
    }


    @Override
    public List<TaskDto> findByEmployee(User employee) {
        List<Task> tasks = taskRepository.findByEmployee(employee);
        return tasks.stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findByProject(Project project) {
        List<Task> tasks = taskRepository.findByProject(project);
        return tasks.stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateStatus(Long taskId, Task.TaskStatus status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Permission check for updating status of tasks
        if (!hasPermission(currentUser, "UPDATE_STATUS_TASK")) {
            throw new AccessDeniedException("You do not have permission to update task status.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Check if the current user is the employee to whom the task is assigned (for Employee role)
        if (!task.getEmployee().equals(currentUser)) {
            throw new AccessDeniedException("You are not authorized to update the status of this task.");
        }

        // Update the task status
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);

        return mapToTaskDto(updatedTask);
    }

    @Override
    public void deleteById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Permission check for creating and assigning tasks
        if (!hasPermission(currentUser, "DELETE_TASK")) {
            throw new AccessDeniedException("You do not have permission to delete tasks !");
        }
        taskRepository.deleteById(id);
    }



    @Override
    public TaskDto createAndAssignTask(Long projectId, Long employeeId, String name, String description, LocalDate dueDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Permission check for creating and assigning tasks
        if (!hasPermission(currentUser, "CREATE_AND_ASSIGN_TASK")) {
            throw new AccessDeniedException("You do not have permission to create and assign tasks.");
        }

        // Fetch the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Check if the current user is the project manager
        if (!project.getProjectManager().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Only the project manager can create and assign tasks for this project.");
        }

        // Validate that the employee is assigned to the project
        boolean isEmployeeAssigned = projectAssignmentRepository.existsByProjectIdAndUserId(projectId, employeeId);
        if (!isEmployeeAssigned) {
            throw new RuntimeException("The employee is not assigned to this project.");
        }

        // Fetch the employee
        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Create the task
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setStatus(Task.TaskStatus.PENDING); // Default status
        task.setProject(project);
        task.setEmployee(employee);

        // Save the task
        Task savedTask = taskRepository.save(task);

        // Send email to the employee
        String subject = "New Task Assigned: " + name;
        String body = "Hello " + employee.getFullName() + ",\n\n"
                + "A new task has been assigned to you.\n\n"
                + "Task Details:\n"
                + "Name: " + name + "\n"
                + "Description: " + description + "\n"
                + "Due Date: " + dueDate + "\n\n"
                + "Project Details:\n"
                + "Project Name: " + project.getName() + "\n"
                + "Project Description: " + project.getDescription() + "\n\n"
                + "Project Manager: " + project.getProjectManager().getFullName() + "\n\n"
                + "Please make sure to complete the task by the due date.\n\n"
                + "Best Regards,\n"
                + "Project Management Team";

        sendEmailNotification(employee.getEmail(), subject, body);

        return mapToTaskDto(savedTask);
    }

    private void sendEmailNotification(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


    private boolean hasPermission(User user, String permission) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(permission));
    }
    private TaskDto mapToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setStatus(task.getStatus());
        taskDto.setProjectId(task.getProject().getId());
        taskDto.setEmployeeId(task.getEmployee().getId());
        return taskDto;
    }
}
