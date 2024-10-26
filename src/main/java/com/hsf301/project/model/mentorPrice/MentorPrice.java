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
    @Column(name = "price_id")
    private Integer PriceID;

    @ManyToOne
    @JoinColumn(name = "mentor_id", referencedColumnName = "user_id")
    private User mentor;

    @Column(name = "price_per_hour", nullable = false)
    private Double pricePerHour;
}
