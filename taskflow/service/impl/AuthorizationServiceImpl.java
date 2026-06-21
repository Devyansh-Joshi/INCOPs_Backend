package com.devyansh.taskflow.service.impl;

import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.ProjectMember;
import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.enums.ProjectRole;
import com.devyansh.taskflow.repository.ProjectMemberRepository;
import com.devyansh.taskflow.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl
        implements AuthorizationService {

    private final ProjectMemberRepository
            projectMemberRepository;

    @Override
    public ProjectMember getMembership(
            User user,
            Project project
    ) {

        return projectMemberRepository
                .findByUserAndProject(user, project)
                .orElseThrow(() ->
                        new RuntimeException(
                                "You are not a member of this project"
                        )
                );
    }

    @Override
    public void validateProjectOwner(
            User user,
            Project project
    ) {

        ProjectMember membership =
                getMembership(user, project);

        if (membership.getRole()
                != ProjectRole.OWNER) {

            throw new RuntimeException(
                    "Only project owner can perform this action"
            );
        }
    }

    @Override
    public void validateProjectAdminOrOwner(
            User user,
            Project project
    ) {

        ProjectMember membership =
                getMembership(user, project);

        if (membership.getRole() != ProjectRole.ADMIN
                &&
                membership.getRole() != ProjectRole.OWNER) {

            throw new RuntimeException(
                    "Only ADMIN or OWNER can perform this action"
            );
        }
    }
}