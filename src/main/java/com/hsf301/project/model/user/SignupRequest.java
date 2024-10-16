package com.hsf301.project.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotNull(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Birth date is required")
    private String birthDate;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Phone number is required")
    private String phone;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;
}