package com.devyansh.taskflow.dto.request;

import com.devyansh.taskflow.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateTaskRequest {

    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    private Priority priority;

    private LocalDate dueDate;
}