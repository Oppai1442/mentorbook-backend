package com.hsf301.project.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsf301.project.model.feedback.Feedback;
import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.model.response.FeedbackResponse;
import com.hsf301.project.repository.FeedbackRepository;
import com.hsf301.project.repository.MentorBookingRepository;
import com.hsf301.project.repository.UserRepository;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    MentorBookingRepository mentorBookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    public Feedback getFeedbackByBookingId(long bookingId) {
        MentorBooking mentorBooking = mentorBookingRepository.findByBookingId(bookingId);
        // Feedback feedback = feedbackRepository.findByBooking(mentorBooking);

        return null;
    }

    public List<FeedbackResponse> getFeedbackByMentorId(Integer mentorId) {
        List<MentorBooking> bookings = mentorBookingRepository.findByMentor_UserId(mentorId);
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
    
        for (MentorBooking booking : bookings) {
            List<Feedback> feedbacks = feedbackRepository.findByBooking(booking);
            for (Feedback feedback : feedbacks) {
                FeedbackResponse response = new FeedbackResponse(feedback, imageService);
                feedbackResponses.add(response);
            }
        }
    
        feedbackResponses.sort(Comparator.comparing(FeedbackResponse::getFeedbackTime).reversed());
    
        return feedbackResponses;
    }
}
