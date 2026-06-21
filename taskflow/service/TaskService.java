package com.devyansh.taskflow.service;

import com.devyansh.taskflow.dto.request.AssignTaskRequest;
import com.devyansh.taskflow.dto.request.CreateTaskRequest;
import com.devyansh.taskflow.dto.request.UpdateTaskStatusRequest;
import com.devyansh.taskflow.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(
            Long projectId,
            CreateTaskRequest request
    );

    List<TaskResponse> getProjectTasks(
            Long projectId
    );

    TaskResponse assignTask(
            Long projectId,
            Long taskId,
            AssignTaskRequest request
    );

    TaskResponse updateTaskStatus(
            Long projectId,
            Long taskId,
            UpdateTaskStatusRequest request
    );
}