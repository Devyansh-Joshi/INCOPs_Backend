package com.devyansh.taskflow.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ValidationErrorResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
