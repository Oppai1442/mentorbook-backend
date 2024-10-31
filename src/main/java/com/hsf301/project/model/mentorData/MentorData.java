package com.hsf301.project.model.mentorData;

import com.hsf301.project.model.user.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "MentorData")
@NoArgsConstructor
@AllArgsConstructor
public class MentorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_data_id")
    private Integer mentorDataId;

    @ManyToOne
    @JoinColumn(name = "mentor_id", referencedColumnName = "user_id")
    private User mentor;

    @Column(name = "mentor_skills", nullable = false, length = 100)
    private String mentorSkills = "[]";

    @Column(name = "mentor_experience", nullable = false, length = 100)
    private Integer mentorExperience = 0;

    @Column(name = "mentor_role", nullable = false, length = 100)
    private String mentorRole = "";
    
    @Column(name = "mentor_rating", nullable = false, length = 1)
    private Double mentorRating = 0.0;
    
    @Column(name = "mentor_price_per_hour", nullable = false)
    private Double mentorPrice = 0.0;
}
