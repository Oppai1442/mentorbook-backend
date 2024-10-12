package com.hsf301.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hsf301.project.model.ApiResponse;
import com.hsf301.project.model.CustomResponse;
import com.hsf301.project.model.ErrorResponse;
import com.hsf301.project.model.LoginRequest;
import com.hsf301.project.model.TokenResponse;
import com.hsf301.project.model.User;
import com.hsf301.project.service.JwtService;
import com.hsf301.project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
       
        if (user != null) {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new ApiResponse<>(new TokenResponse(token), new CustomResponse("user", user)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new ApiResponse<>(new ErrorResponse("Invalid credentials")));
        }
    }
}