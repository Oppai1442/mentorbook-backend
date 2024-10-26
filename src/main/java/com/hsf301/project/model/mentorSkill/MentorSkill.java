package com.hsf301.project.model.mentorSkill;

import com.hsf301.project.model.user.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "MentorSkills")
@NoArgsConstructor
@AllArgsConstructor
public class MentorSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_skill_id")
    private Integer mentorSkillId;

    @ManyToOne
    @JoinColumn(name = "mentor_id", referencedColumnName = "user_id")
    private User mentor;

    @Column(name = "skills", nullable = false, length = 100)
    private String skills;
}
