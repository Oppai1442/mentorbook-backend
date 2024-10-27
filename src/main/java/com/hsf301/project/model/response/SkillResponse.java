package com.hsf301.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponse {
    private Integer skillId;
    private String skillName;
    private String skillDescription;
}
