package com.devyansh.taskflow.service;

import com.devyansh.taskflow.dto.request.CreateUserRequest;
import com.devyansh.taskflow.dto.response.UserResponse;

import java.util.List;
public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUser(Long id);
}
