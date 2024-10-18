package com.hsf301.project.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String role;

    public UserResponse(User user) {
        this.id = user.getUserId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
