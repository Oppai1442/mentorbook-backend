package com.hsf301.project.model.response;

import java.util.List;

import com.hsf301.project.model.skillCatalogue.SkillCatalogue;
import com.hsf301.project.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorResponse {
    private Integer userId;
    private String fullName;
    private String email;
    private List<SkillCatalogue> skills;
}
