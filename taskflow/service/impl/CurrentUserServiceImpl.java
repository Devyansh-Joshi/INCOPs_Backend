package com.devyansh.taskflow.service.impl;

import com.devyansh.taskflow.entity.User;
import com.devyansh.taskflow.repository.UserRepository;
import com.devyansh.taskflow.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl
        implements CurrentUserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }
}
