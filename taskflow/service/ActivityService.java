package com.devyansh.taskflow.service;

import com.devyansh.taskflow.dto.response.ProjectActivityResponse;
import com.devyansh.taskflow.dto.response.TaskActivityResponse;
import com.devyansh.taskflow.entity.Task;
import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.enums.ActivityAction;

import java.util.List;

public interface ActivityService {

    void log(
            Task task,
            User user,
            ActivityAction action,
            String details
    );

    List<TaskActivityResponse> getTaskActivity(
            Long taskId,
            User currentUser
    );

    List<ProjectActivityResponse>
    getProjectActivity(
            Long projectId,
            User currentUser
    );
}
