package com.devyansh.taskflow.repository;

import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);
}