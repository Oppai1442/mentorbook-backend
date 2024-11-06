package com.hsf301.project.controller;

import com.hsf301.project.model.feedback.Feedback;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.FeedbackResponse;
import com.hsf301.project.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<Feedback>> getFeedbackByBookingId(@PathVariable Long bookingId) {
        Feedback feedback = feedbackService.getFeedbackByBookingId(bookingId);

        return ResponseEntity.ok(new ApiResponse<Feedback>(feedback));
    }

    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbackByMentorId(@PathVariable Integer mentorId) {
        List<FeedbackResponse> feedbackList = feedbackService.getFeedbackByMentorId(mentorId);
        
        return ResponseEntity.ok(new ApiResponse<List<FeedbackResponse>>(feedbackList));
    }
}
