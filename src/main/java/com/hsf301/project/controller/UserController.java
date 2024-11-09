package com.hsf301.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsf301.project.model.request.LoginRequest;
import com.hsf301.project.model.request.ResetPasswordRequest;
import com.hsf301.project.model.request.SetRoleRequest;
import com.hsf301.project.model.request.SetStatusRequest;
import com.hsf301.project.model.request.SignupRequest;
import com.hsf301.project.model.request.TokenRequest;
import com.hsf301.project.model.request.UserRequest;
import com.hsf301.project.model.request.UserUpdateProfileRequest;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.AuthResponse;
import com.hsf301.project.model.response.UserResponseContainer;
import com.hsf301.project.service.UserService;

import com.hsf301.project.model.user.User;

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

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<AuthResponse>> update(@Valid @RequestBody UserUpdateProfileRequest updateRequest) {
        AuthResponse authResponse = userService.UserUpdateProfileRequest(updateRequest);

        return ResponseEntity.ok(new ApiResponse<AuthResponse>(authResponse));
    }
    @PostMapping("/get-user")
    public UserResponseContainer getUsers(@RequestBody UserRequest request) {
        return userService.getUsersByPage(request.getUserId(), request.getCurrentPage(), request.getResultCount());
    }

    @PostMapping("/update-role")
    public ResponseEntity<String> setUserRole(@RequestBody SetRoleRequest request) {
        System.out.println(request);
        userService.updateUserRole(request.getUserId(), request.getValue());
        return ResponseEntity.ok("User role updated successfully");
    }

    @PostMapping("/update-status")
    public ResponseEntity<String> setUserStatus(@RequestBody SetStatusRequest request) {
        userService.updateUserStatus(request.getUserId(), request.getValue());
        return ResponseEntity.ok("User status updated successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetUserPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetUserPassword(request.getUserId(), "987654321");
        return ResponseEntity.ok("User password reset successfully");
    }
}