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
        String url = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d19347.62494047712!2d106.81797901185585!3d10.849041789835704!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752731176b07b1%3A0xb752b24b379bae5e!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFQgVFAuIEhDTQ!5e1!3m2!1svi!2s!4v1726982911231!5m2!1svi!2s";
        
        return ResponseEntity.ok(Map.of("url", url));
    }
}