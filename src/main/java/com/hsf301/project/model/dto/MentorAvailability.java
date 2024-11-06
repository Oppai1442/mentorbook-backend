package com.hsf301.project.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorAvailability {
    private String day;
    private List<TimeSlot> timeSlots;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class TimeSlot {
    @JsonProperty("start")
    private String start;
    
    @JsonProperty("end")
    private String end;
}
