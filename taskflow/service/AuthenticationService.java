package com.devyansh.taskflow.service;

import com.devyansh.taskflow.dto.request.LoginRequest;
import com.devyansh.taskflow.dto.response.AuthResponse;

public interface AuthenticationService {

    AuthResponse login(LoginRequest request);
}
