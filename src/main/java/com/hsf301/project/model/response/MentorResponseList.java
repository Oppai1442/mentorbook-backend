package com.hsf301.project.model.response;

import java.util.List;

public class MentorResponseList {
    private List<MentorResponse> mentors;
    private int totalFound;

    public MentorResponseList(List<MentorResponse> mentors, int totalFound) {
        this.mentors = mentors;
        this.totalFound = totalFound;
    }

    public List<MentorResponse> getMentors() {
        return mentors;
    }

    public int getTotalFound() {
        return totalFound;
    }
}