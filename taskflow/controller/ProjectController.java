package com.devyansh.taskflow.controller;

import com.devyansh.taskflow.dto.request.AddMemberRequest;
import com.devyansh.taskflow.dto.request.CreateProjectRequest;
import com.devyansh.taskflow.dto.response.ProjectResponse;
import com.devyansh.taskflow.dto.response.ProjectMemberResponse;
import com.devyansh.taskflow.dto.request.UpdateProjectRequest;
import com.devyansh.taskflow.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody
            CreateProjectRequest request
    ) {

        return projectService
                .createProject(request);
    }

    @PutMapping("/{projectId}")
    public ProjectResponse updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody
            UpdateProjectRequest request
    ) {

        return projectService.updateProject(
                projectId,
                request
        );
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(
            @PathVariable Long projectId
    ) {

        projectService.deleteProject(projectId);
    }

    @GetMapping
    public List<ProjectResponse> getMyProjects() {

        return projectService.getMyProjects();
    }

    @GetMapping("/{projectId}")
    public ProjectResponse getProject(
            @PathVariable Long projectId
    ) {

        return projectService
                .getProject(projectId);
    }

    @PostMapping("/{projectId}/members")
    public void addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody
            AddMemberRequest request
    ) {

        projectService.addMember(
                projectId,
                request
        );
    }

    @GetMapping("/{projectId}/members")
    public List<ProjectMemberResponse>
    getProjectMembers(
            @PathVariable Long projectId
    ) {

        return projectService
                .getProjectMembers(projectId);
    }
}