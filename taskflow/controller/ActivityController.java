package com.devyansh.taskflow.controller;

import com.devyansh.taskflow.dto.response.ProjectActivityResponse;
import com.devyansh.taskflow.dto.response.TaskActivityResponse;
import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.repository.UserRepository;
import com.devyansh.taskflow.service.ActivityService;
import com.devyansh.taskflow.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    private final UserRepository userRepository;

    private final CurrentUserService currentUserService;

    @GetMapping("tasks/{taskId}/activity")
    public List<TaskActivityResponse> getTaskActivity(
            @PathVariable Long taskId
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        return activityService
                .getTaskActivity(
                        taskId,
                        currentUser
                );
    }

    @GetMapping("/projects/{projectId}/activity")
    public List<ProjectActivityResponse>
    getProjectActivity(
            @PathVariable Long projectId
    ) {

        User currentUser = currentUserService.getCurrentUser();

        return activityService
                .getProjectActivity(
                        projectId,
                        currentUser
                );
    }
}