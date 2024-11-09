package com.hsf301.project.model.response;

import com.hsf301.project.model.feedback.Feedback;
import com.hsf301.project.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Integer feedbackId;
    private String comment;
    private Integer rating;
    private LocalDateTime feedbackTime;
    private Integer userId;
    private String fullName;
    private String userAvatarUrl;

    public FeedbackResponse(Feedback feedback, ImageService imageService) {
        this.feedbackId = feedback.getFeedbackId();
        this.comment = feedback.getComment();
        this.rating = feedback.getRating();
        this.feedbackTime = feedback.getFeedbackTime();
        this.userId = feedback.getBooking().getCustomer().getUserId();
        this.fullName = feedback.getBooking().getCustomer().getFullName();
        this.userAvatarUrl = imageService.getImageUrl(feedback.getBooking().getCustomer().getAvatar());
    }
}
