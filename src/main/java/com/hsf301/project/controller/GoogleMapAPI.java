package com.hsf301.project.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maps")
@CrossOrigin(origins = "http://localhost:3000")
public class GoogleMapAPI {

    @GetMapping("/geocode")
    public ResponseEntity<Map<String, String>> getURL() {
        String url = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d38696.80650538212!2d106.84005865053703!3d10.837008514890446!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317521d28bd24323%3A0x6e003b0f1ab73e2f!2sOrigami%20T%C3%B2a%20S10.07!5e1!3m2!1svi!2s!4v1727612264208!5m2!1svi!2s";
        
        return ResponseEntity.ok(Map.of("url", url));
    }
}