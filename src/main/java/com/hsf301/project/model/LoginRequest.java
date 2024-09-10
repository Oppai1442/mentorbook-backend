package com.hsf301.project.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}