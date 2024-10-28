package com.hsf301.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.hsf301.project.model.dto.Price;
import com.hsf301.project.model.dto.Sorting;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorRequest {
    private Integer page = 0;
    private List<Integer> skillIds = new ArrayList<Integer>();
    private List<Integer> rating = new ArrayList<Integer>();
    private Price prices;
    private Sorting sorting;
}