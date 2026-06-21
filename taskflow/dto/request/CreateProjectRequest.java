package com.devyansh.taskflow.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequest {

    @NotBlank(message = "Project name is required")
    private String name;

    private String description;
}

