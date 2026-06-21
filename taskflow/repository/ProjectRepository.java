package com.devyansh.taskflow.repository;

import com.devyansh.taskflow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository
        extends JpaRepository<Project, Long> {
}