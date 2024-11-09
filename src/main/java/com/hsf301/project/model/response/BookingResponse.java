package com.hsf301.project.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Integer id;
    private User customer;
    private User mentor;
    private LocalDateTime bookingTime;
    private LocalDateTime startTime;
    private Double duration;
    private String status;
    private String meetUrl;
    private String description;
    private BigDecimal cost;
}
