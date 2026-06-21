package com.devyansh.taskflow.dto.response;

import com.devyansh.taskflow.enums.Priority;
import com.devyansh.taskflow.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private Priority priority;

    private LocalDate dueDate;

    private String assignedUserEmail;
}