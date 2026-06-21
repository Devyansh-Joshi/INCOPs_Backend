package com.devyansh.taskflow.dto.request;

import com.devyansh.taskflow.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskStatusRequest {

    @NotNull
    private TaskStatus status;
}