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
    private int skillID;  // renamed to camel case

    @Column(length = 255)
    private String skillName;

    @Column(length = 255)
    private String description;
}
