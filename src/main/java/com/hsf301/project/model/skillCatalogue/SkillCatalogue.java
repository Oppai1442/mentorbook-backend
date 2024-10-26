package com.hsf301.project.model.skillCatalogue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SkillCatalogue")
@NoArgsConstructor
@AllArgsConstructor
public class SkillCatalogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillID;

    @Column(name = "skill_name", length = 255)
    private String skillName;

    @Column(name = "skill_description", length = 255)
    private String skillDescription;
}
