package com.devyansh.taskflow.service.impl;

import com.devyansh.taskflow.dto.request.AssignTaskRequest;
import com.devyansh.taskflow.dto.request.CreateTaskRequest;
import com.devyansh.taskflow.dto.request.UpdateTaskStatusRequest;
import com.devyansh.taskflow.dto.response.TaskResponse;
import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.Task;
import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.enums.ActivityAction;
import com.devyansh.taskflow.enums.TaskStatus;
import com.devyansh.taskflow.repository.ProjectRepository;
import com.devyansh.taskflow.repository.TaskRepository;
import com.devyansh.taskflow.repository.UserRepository;
import com.devyansh.taskflow.service.ActivityService;
import com.devyansh.taskflow.service.AuthorizationService;
import com.devyansh.taskflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final AuthorizationService authorizationService;
    private final ActivityService activityService;

    @Override
    public TaskResponse createTask(
            Long projectId,
            CreateTaskRequest request
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                )
                        );

        /*
            ONLY PROJECT MEMBERS
            CAN CREATE TASKS
        */
        authorizationService.getMembership(
                currentUser,
                project
        );

        Task task = Task.builder()
                .title(request.getTitle())
                .description(
                        request.getDescription()
                )
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .status(TaskStatus.TODO)
                .project(project)
                .build();

        Task savedTask =
                taskRepository.save(task);

        System.out.println(
                "Saved Task ID = "
                        + savedTask.getId()
        );

        activityService.log(
                savedTask,
                currentUser,
                ActivityAction.TASK_CREATED,
                "Task created"
        );

        return mapToResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getProjectTasks(
            Long projectId
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                )
                        );

        authorizationService.getMembership(
                currentUser,
                project
        );

        return taskRepository
                .findByProject(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaskResponse updateTaskStatus(
            Long projectId,
            Long taskId,
            UpdateTaskStatusRequest request
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                )
                        );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found"
                                )
                        );

        boolean isAssignedUser =
                task.getAssignedUser() != null
                        &&
                        task.getAssignedUser()
                                .getId()
                                .equals(currentUser.getId());

        boolean isAdminOrOwner = false;

        try {

            authorizationService
                    .validateProjectAdminOrOwner(
                            currentUser,
                            project
                    );

            isAdminOrOwner = true;

        } catch (Exception ignored) {
        }

        if (!isAssignedUser &&
                !isAdminOrOwner) {

            throw new RuntimeException(
                    "You cannot update this task"
            );
        }

        TaskStatus oldStatus =
                task.getStatus();

        task.setStatus(request.getStatus());

        Task updatedTask =
                taskRepository.save(task);

        activityService.log(
                updatedTask,
                currentUser,
                ActivityAction.STATUS_CHANGED,
                oldStatus + " -> " + request.getStatus()
        );

        return mapToResponse(updatedTask);
    }

    @Override
    public TaskResponse assignTask(
            Long projectId,
            Long taskId,
            AssignTaskRequest request
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                )
                        );

        authorizationService
                .validateProjectAdminOrOwner(
                        currentUser,
                        project
                );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found"
                                )
                        );

        User assignedUser =
                userRepository.findByEmail(
                        request.getUserEmail()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

    /*
        USER MUST BELONG TO PROJECT
    */
        authorizationService.getMembership(
                assignedUser,
                project
        );

        task.setAssignedUser(assignedUser);

        Task updatedTask =
                taskRepository.save(task);

        activityService.log(
                updatedTask,
                currentUser,
                ActivityAction.TASK_ASSIGNED,
                "Assigned to "
                        + assignedUser.getEmail()
        );

        return mapToResponse(updatedTask);
    }

    private TaskResponse mapToResponse(
            Task task
    ) {

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(
                        task.getDescription()
                )
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assignedUserEmail(
                        task.getAssignedUser() != null
                                ? task.getAssignedUser()
                                .getEmail()
                                : null
                )
                .build();
    }
}