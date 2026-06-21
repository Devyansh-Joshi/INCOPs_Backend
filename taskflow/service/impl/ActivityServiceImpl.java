package com.devyansh.taskflow.service.impl;

import com.devyansh.taskflow.dto.response.ProjectActivityResponse;
import com.devyansh.taskflow.dto.response.TaskActivityResponse;
import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.Task;
import com.devyansh.taskflow.entity.TaskActivity;
import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.enums.ActivityAction;
import com.devyansh.taskflow.repository.ProjectRepository;
import com.devyansh.taskflow.repository.TaskActivityRepository;
import com.devyansh.taskflow.repository.TaskRepository;
import com.devyansh.taskflow.service.ActivityService;
import com.devyansh.taskflow.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ActivityServiceImpl
        implements ActivityService {

    private final TaskActivityRepository repository;
    private final TaskRepository taskRepository;
    private final AuthorizationService authorizationService;
    private final ProjectRepository projectRepository;

    @Override
    public void log(
            Task task,
            User user,
            ActivityAction action,
            String details
    ) {
        System.out.println(
                "Logging Activity For Task ID = "
                        + task.getId()
        );

        TaskActivity activity =
                TaskActivity.builder()
                        .task(task)
                        .user(user)
                        .action(action)
                        .details(details)
                        .build();

        repository.save(activity);
    }

    @Override
    public List<ProjectActivityResponse>
    getProjectActivity(
            Long projectId,
            User currentUser
    ) {

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                )
                        );

    /*
        ONLY PROJECT MEMBERS
        CAN VIEW PROJECT ACTIVITY
     */
        authorizationService.getMembership(
                currentUser,
                project
        );

        return repository
                .findByTaskProjectOrderByCreatedAtDesc(
                        project
                )
                .stream()
                .map(activity ->
                        ProjectActivityResponse.builder()
                                .taskTitle(
                                        activity.getTask()
                                                .getTitle()
                                )
                                .action(
                                        activity.getAction()
                                )
                                .performedBy(
                                        activity.getUser()
                                                .getEmail()
                                )
                                .details(
                                        activity.getDetails()
                                )
                                .createdAt(
                                        activity.getCreatedAt()
                                )
                                .build()
                )
                .toList();
    }

    @Override
    public List<TaskActivityResponse> getTaskActivity(
            Long taskId,
            User currentUser
    ) {

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found"
                                )
                        );

        authorizationService.getMembership(
                currentUser,
                task.getProject()
        );

        return repository
                .findByTaskOrderByCreatedAtDesc(task)
                .stream()
                .map(activity ->
                        TaskActivityResponse.builder()
                                .action(activity.getAction())
                                .performedBy(
                                        activity.getUser()
                                                .getEmail()
                                )
                                .details(
                                        activity.getDetails()
                                )
                                .createdAt(
                                        activity.getCreatedAt()
                                )
                                .build()
                )
                .toList();
    }
}