package com.hsf301.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String backgroundUrl;
    private String phone;
    private LocalDate birthDate;
    private String gender;

    public UserResponse(User user, ImageService imageService) {
        this.id = user.getUserId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.avatarUrl = imageService.getImageUrl(user.getAvatar());
        this.backgroundUrl = imageService.getImageUrl(user.getBackground());
        this.phone = user.getPhoneNumber();
        this.birthDate = user.getBirthDate();
        this.gender = user.getGender();
    }
}
