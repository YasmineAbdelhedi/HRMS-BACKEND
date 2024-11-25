package com.example.hrms.repository;

import com.example.hrms.entity.Project;
import com.example.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByProjectManager(User projectManager);
    List<Project> findByProjectManagerId(Long projectManagerId);

}
