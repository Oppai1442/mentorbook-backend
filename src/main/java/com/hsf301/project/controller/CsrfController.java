package com.hsf301.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;

@RestController
@RequestMapping("/csrf") // Hoặc một đường dẫn khác mà bạn muốn
public class CsrfController {

    @GetMapping("/generate-token")
    public CsrfToken csrf(CsrfToken token) {
        return token; // Trả về token CSR
    }
}