package com.devyansh.taskflow.service.impl;

import com.devyansh.taskflow.dto.request.CreateProjectRequest;
import com.devyansh.taskflow.dto.response.ProjectMemberResponse;
import com.devyansh.taskflow.dto.response.ProjectResponse;
import com.devyansh.taskflow.dto.request.UpdateProjectRequest;
import com.devyansh.taskflow.dto.request.AddMemberRequest;
import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.ProjectMember;
import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.enums.ProjectRole;
import com.devyansh.taskflow.repository.ProjectMemberRepository;
import com.devyansh.taskflow.repository.ProjectRepository;
import com.devyansh.taskflow.repository.UserRepository;
import com.devyansh.taskflow.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl
        implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMemberRepository
            projectMemberRepository;

    private final UserRepository userRepository;

    private ProjectMember getMembership(
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

    private void validateProjectOwner(
            User user,
            Project project
    ) {

        ProjectMember membership =
                getMembership(user, project);

        if (membership.getRole() !=
                ProjectRole.OWNER) {

            throw new RuntimeException(
                    "Only project owner can perform this action"
            );
        }
    }

    @Override
    public ProjectResponse createProject(
            CreateProjectRequest request
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Project savedProject =
                projectRepository.save(project);

        ProjectMember membership =
                ProjectMember.builder()
                        .user(currentUser)
                        .project(savedProject)
                        .role(ProjectRole.OWNER)
                        .build();

        projectMemberRepository.save(membership);

        return mapToResponse(savedProject);
    }

    @Override
    public ProjectResponse updateProject(
            Long projectId,
            UpdateProjectRequest request
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

        validateProjectOwner(
                currentUser,
                project
        );

        project.setName(request.getName());
        project.setDescription(
                request.getDescription()
        );

        Project updatedProject =
                projectRepository.save(project);

        return mapToResponse(updatedProject);
    }

    @Transactional
    @Override
    public void deleteProject(Long projectId) {

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

        validateProjectOwner(
                currentUser,
                project
        );

        projectMemberRepository.deleteByProject(project);
        projectRepository.delete(project);
    }

    @Transactional
    @Override
    public void addMember(
            Long projectId,
            AddMemberRequest request
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

        validateProjectOwner(
                currentUser,
                project
        );

        User newMember =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        boolean alreadyExists =
                projectMemberRepository
                        .existsByUserAndProject(
                                newMember,
                                project
                        );

        if (alreadyExists) {

            throw new RuntimeException(
                    "User is already a member"
            );
        }

        ProjectMember member =
                ProjectMember.builder()
                        .user(newMember)
                        .project(project)
                        .role(ProjectRole.MEMBER)
                        .build();

        projectMemberRepository.save(member);
    }

    @Override
    public List<ProjectMemberResponse>
    getProjectMembers(Long projectId) {

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

        getMembership(currentUser, project);

        return projectMemberRepository
                .findByProject(project)
                .stream()
                .map(member ->
                        ProjectMemberResponse.builder()
                                .userId(
                                        member.getUser().getId()
                                )
                                .name(
                                        member.getUser().getName()
                                )
                                .email(
                                        member.getUser().getEmail()
                                )
                                .role(member.getRole())
                                .build()
                )
                .toList();
    }

    @Override
    public List<ProjectResponse> getMyProjects() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        return projectMemberRepository
                .findByUser(currentUser)
                .stream()
                .map(ProjectMember::getProject)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProjectResponse getProject(
            Long projectId
    ) {

        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"
                                )
                        );

        return mapToResponse(project);
    }

    private ProjectResponse mapToResponse(
            Project project
    ) {

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }

}