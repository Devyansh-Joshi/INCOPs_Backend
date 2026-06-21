package com.devyansh.taskflow.dto.response;

import com.devyansh.taskflow.enums.ActivityAction;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectActivityResponse {

    private String taskTitle;

    private ActivityAction action;

    private String performedBy;

    private String details;

    private LocalDateTime createdAt;
}