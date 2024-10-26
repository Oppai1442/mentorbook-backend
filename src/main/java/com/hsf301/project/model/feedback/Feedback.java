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
    @Column(name = "feedback_id")
    private Integer feedbackId;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id")
    private MentorBooking booking;

    @Column(name="rating",nullable = false)
    private Integer rating;

    @Column(name="comment",length = 500)
    private String comment;
}
