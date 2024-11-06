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

    @Column(name = "mentor_skills", nullable = false)
    private String mentorSkills = "[]";

    @Column(name = "mentor_experience", nullable = false)
    private Integer mentorExperience = 0;

    @Column(name = "mentor_role", nullable = false, columnDefinition = "nvarchar(max)")
    private String mentorRole = "";
    
    @Column(name = "mentor_rating", nullable = false, columnDefinition = "decimal(3,2)")
    private Double mentorRating = 0.0;
    
    @Column(name = "mentor_price_per_hour", nullable = false, columnDefinition = "decimal(10,2)")
    private Double mentorPrice = 0.0;
    
    @Column(name = "mentor_description", length = 255)
    private String mentorDescription;

    @Column(name = "mentor_available_time", nullable = false, columnDefinition = "nvarchar(max)")
    private String mentorAvailableTime = "[]";
}
