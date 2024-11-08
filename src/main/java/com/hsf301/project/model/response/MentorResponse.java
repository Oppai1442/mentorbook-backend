package com.hsf301.project.model.response;

import java.util.List;

import com.hsf301.project.model.dto.MentorAvailability;
import com.hsf301.project.model.skills.Skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorResponse {
    private Integer userId; //
    private String fullName; //
    private String email; //
    private String avatarUrl; //
    private String backgroundUrl; //
    private List<Skills> skills; //
    private Double price; //
    private Double rating; //
    private Integer totalBooked; //
    private Integer experience;
    private String role; //
    private String description; //
    private List<MentorAvailability> availableTime; //
}
