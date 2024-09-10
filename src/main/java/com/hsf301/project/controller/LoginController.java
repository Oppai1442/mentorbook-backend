package com.hsf301.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hsf301.project.model.LoginRequest;
import com.hsf301.project.model.User;
import com.hsf301.project.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Gọi service để xác thực
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}