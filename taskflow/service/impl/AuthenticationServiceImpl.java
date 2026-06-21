package com.devyansh.taskflow.service.impl;

import com.devyansh.taskflow.dto.request.LoginRequest;
import com.devyansh.taskflow.dto.response.AuthResponse;
import com.devyansh.taskflow.security.JwtService;
import com.devyansh.taskflow.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request){
        System.out.println(
                "Login attempt: "
                        + request.getEmail()
        );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(
                request.getEmail()
        );

        System.out.println(
                "Authentication successful"
        );

        return new AuthResponse(token);
    }
}
