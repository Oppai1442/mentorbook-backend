package com.hsf301.project.model.mentorPrice;

import com.hsf301.project.model.user.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "MentorPrice")
@NoArgsConstructor
@AllArgsConstructor
public class MentorPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceId;

    @ManyToOne
    @JoinColumn(name = "mentorId", referencedColumnName = "userId")
    private User mentor;

    @Column(nullable = false)
    private Double pricePerHour;
}

