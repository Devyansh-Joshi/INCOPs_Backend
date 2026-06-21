package com.devyansh.taskflow.repository;

import com.devyansh.taskflow.dto.response.TaskActivityResponse;
import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.Task;
import com.devyansh.taskflow.entity.TaskActivity;
import com.devyansh.taskflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskActivityRepository
        extends JpaRepository<TaskActivity, Long> {

    List<TaskActivity> findByTaskOrderByCreatedAtDesc(
            Task task
    );

    List<TaskActivity>
    findByTaskProjectOrderByCreatedAtDesc(
            Project project
    );
}