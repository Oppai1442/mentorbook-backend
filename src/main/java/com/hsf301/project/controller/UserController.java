package com.hsf301.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hsf301.project.exception.EmailConflictException;
import com.hsf301.project.model.ApiResponse;
import com.hsf301.project.model.AuthResponse;
import com.hsf301.project.model.ErrorResponse;
import com.hsf301.project.model.LoginRequest;
import com.hsf301.project.model.user.SignupRequest;
import com.hsf301.project.model.user.User;
import com.hsf301.project.model.user.UserResponse;
import com.hsf301.project.service.JwtService;
import com.hsf301.project.service.UserService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
       
        if (user != null) {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new ApiResponse<AuthResponse>(new AuthResponse(token, new UserResponse(user))));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new ErrorResponse("Invalid credentials"));
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody SignupRequest signupRequest) {
        User user = userService.register(signupRequest);
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new ApiResponse<AuthResponse>(new AuthResponse(token, new UserResponse(user))));
    }

}