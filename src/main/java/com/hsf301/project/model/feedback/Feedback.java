package com.hsf301.project.model.feedback;

import com.hsf301.project.model.mentorBooking.MentorBooking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Feedbacks")
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @ManyToOne
    @JoinColumn(name = "bookingId", referencedColumnName = "bookingId")
    private MentorBooking booking;

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 500)
    private String comment;
}
