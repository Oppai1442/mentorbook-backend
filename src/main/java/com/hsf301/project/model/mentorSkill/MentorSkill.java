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
    private Integer mentorSkillId;

    @ManyToOne
    @JoinColumn(name = "mentorId", referencedColumnName = "userId")
    private User mentor;

    @Column(nullable = false, length = 100)
    private String skillName;
}
