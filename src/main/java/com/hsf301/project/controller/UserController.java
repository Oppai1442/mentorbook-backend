package com.hsf301.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsf301.project.model.request.LoginRequest;
import com.hsf301.project.model.request.SignupRequest;
import com.hsf301.project.model.request.TokenRequest;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.AuthResponse;
import com.hsf301.project.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = userService.authenticate(loginRequest);

        return ResponseEntity.ok(new ApiResponse<AuthResponse>(authResponse));
    }

    @PostMapping("/login-token")
    public ResponseEntity<ApiResponse<AuthResponse>> loginWithToken(@Valid @RequestBody TokenRequest tokenRequest) {
        AuthResponse authResponse = userService.authenticate(tokenRequest);

        return ResponseEntity.ok(new ApiResponse<AuthResponse>(authResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody SignupRequest signupRequest) {
        AuthResponse authResponse = userService.register(signupRequest);

        return ResponseEntity.ok(new ApiResponse<AuthResponse>(authResponse));
    }
}