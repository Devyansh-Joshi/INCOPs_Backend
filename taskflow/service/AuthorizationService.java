package com.devyansh.taskflow.service;

import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.ProjectMember;
import com.devyansh.taskflow.entity.User;

public interface AuthorizationService {

    ProjectMember getMembership(
            User user,
            Project project
    );

    void validateProjectOwner(
            User user,
            Project project
    );

    void validateProjectAdminOrOwner(
            User user,
            Project project
    );
}