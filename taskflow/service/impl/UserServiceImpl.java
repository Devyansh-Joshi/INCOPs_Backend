package com.devyansh.taskflow.service.impl;

import com.devyansh.taskflow.dto.request.CreateUserRequest;
import com.devyansh.taskflow.dto.response.UserResponse;
import com.devyansh.taskflow.enums.Role;
import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.exception.ResourceNotFoundException;
import com.devyansh.taskflow.repository.UserRepository;
import com.devyansh.taskflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id)
                );
        return mapToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
