package com.hsf301.project.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseContainer {
    private Double totalFound;
    private List<BookingResponse> bookingResponse;
}