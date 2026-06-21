package com.devyansh.taskflow.dto.response;

import com.devyansh.taskflow.enums.ProjectRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberResponse {

    private Long userId;

    private String name;

    private String email;

    private ProjectRole role;
}