package com.devyansh.taskflow.controller;

import com.devyansh.taskflow.dto.request.AssignTaskRequest;
import com.devyansh.taskflow.dto.request.CreateTaskRequest;
import com.devyansh.taskflow.dto.request.UpdateTaskStatusRequest;
import com.devyansh.taskflow.dto.response.TaskResponse;
import com.devyansh.taskflow.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody
            CreateTaskRequest request
    ) {

        return taskService.createTask(
                projectId,
                request
        );
    }

    @GetMapping
    public List<TaskResponse> getProjectTasks(
            @PathVariable Long projectId
    ) {

        return taskService
                .getProjectTasks(projectId);
    }

    @PutMapping("/{taskId}/assign")
    public TaskResponse assignTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody
            AssignTaskRequest request
    ) {

        return taskService.assignTask(
                projectId,
                taskId,
                request
        );
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateTaskStatus(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody
            UpdateTaskStatusRequest request
    ) {

        return taskService.updateTaskStatus(
                projectId,
                taskId,
                request
        );
    }
}