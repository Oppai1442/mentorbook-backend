package com.hsf301.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.feedback.Feedback;
import com.hsf301.project.model.mentorBooking.MentorBooking;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByBooking(MentorBooking booking);
}