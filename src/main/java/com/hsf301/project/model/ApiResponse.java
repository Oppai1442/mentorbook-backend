package com.hsf301.project.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private List<Object> data;

    public ApiResponse(Object... data) {
        this.data = new ArrayList<>();
        for (Object item : data) {
            this.data.add(item);
        }
    }
}
