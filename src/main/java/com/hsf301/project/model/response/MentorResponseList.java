package com.hsf301.project.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorResponseList {
    private List<MentorResponse> mentors;
    private int totalFound;
}