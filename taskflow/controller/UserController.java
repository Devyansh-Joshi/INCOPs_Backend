package com.devyansh.taskflow.controller;

import com.devyansh.taskflow.dto.request.CreateUserRequest;
import com.devyansh.taskflow.dto.response.UserResponse;
import com.devyansh.taskflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser (
            @Valid @RequestBody CreateUserRequest request
    ){
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    public String profile() {

        return "Access granted";
    }

    @GetMapping("/admin")
    public String adminOnly() {

        return "Admin access granted";
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(
            @PathVariable Long id
    ) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
    }
}
