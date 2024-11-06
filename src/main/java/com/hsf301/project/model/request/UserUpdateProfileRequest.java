package com.hsf301.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateProfileRequest {
    private String fullName;
    private String birthDate;
    private String gender;
    private String phone;
    private String email;
}
