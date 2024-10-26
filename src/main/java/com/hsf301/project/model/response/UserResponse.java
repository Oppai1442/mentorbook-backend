package com.hsf301.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hsf301.project.model.user.User;
import com.hsf301.project.service.ImageService;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String role;
    private String avatarUrl;

    public UserResponse(User user, ImageService imageService) {
        this.id = user.getUserId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.avatarUrl = imageService.getImageUrl(user.getAvatar());
    }
}
