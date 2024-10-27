package com.hsf301.project.model.skills;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Skills")
@NoArgsConstructor
@AllArgsConstructor
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillID;

    @Column(name = "skill_name", length = 255)
    private String skillName;

    @Column(name = "skill_description", length = 255)
    private String skillDescription;
}
