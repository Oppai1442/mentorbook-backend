package com.hsf301.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorBookUpdateStatusRequest {
    private Long bookingId;
    private String status;
}