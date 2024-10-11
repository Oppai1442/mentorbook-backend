package com.hsf301.project.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {
    private String object;
    private List<Object> data;

    public CustomResponse(String object, Object... data) {
        this.object = object;
        this.data = new ArrayList<>();
        for (Object item : data) {
            this.data.add(item);
        }

    }
}
