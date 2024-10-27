package com.hsf301.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsf301.project.model.feedback.Feedback;
import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.repository.FeedbackRepository;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    
    public List<Feedback> findByBooking(MentorBooking mentorBooking) {
        
        return feedbackRepository.findByBooking(mentorBooking);
    }
}
