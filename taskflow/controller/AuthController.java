package com.devyansh.taskflow.controller;

import com.devyansh.taskflow.dto.request.LoginRequest;
import com.devyansh.taskflow.dto.response.AuthResponse;
import com.devyansh.taskflow.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
    ) {

        return authenticationService.login(request);
    }
}