package com.hsf301.project.model.response;

import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorBookingResponse {
    private Integer id;
    private User customer;
    private User mentor;
    private LocalDateTime date;
    private Double duration;
}
