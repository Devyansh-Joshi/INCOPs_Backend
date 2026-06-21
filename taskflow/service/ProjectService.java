package com.devyansh.taskflow.service;

import com.devyansh.taskflow.dto.request.CreateProjectRequest;
import com.devyansh.taskflow.dto.response.ProjectResponse;
import com.devyansh.taskflow.dto.request.UpdateProjectRequest;
import com.devyansh.taskflow.dto.request.AddMemberRequest;
import com.devyansh.taskflow.dto.response.ProjectMemberResponse;
import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(
            CreateProjectRequest request
    );

    ProjectResponse updateProject(
            Long projectId,
            UpdateProjectRequest request
    );

    void deleteProject(Long projectId);

    List<ProjectResponse> getMyProjects();

    void addMember(
            Long projectId,
            AddMemberRequest request
    );

    List<ProjectMemberResponse> getProjectMembers(
            Long projectId
    );

    ProjectResponse getProject(Long projectId);
}