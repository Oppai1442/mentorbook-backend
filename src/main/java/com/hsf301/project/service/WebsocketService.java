package com.hsf301.project.service;

import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    public String processMessage(String message) {
        return "Processed: " + message;
    }
}
