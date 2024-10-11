package com.hsf301.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsf301.project.model.ApiResponse;
import com.hsf301.project.model.ErrorResponse;
import com.hsf301.project.model.TokenResponse;

import org.springframework.security.web.csrf.CsrfToken;

@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping("/generate-token")
    public ResponseEntity<ApiResponse<Object>> csrf(CsrfToken token) {
        if (token != null) {
            return ResponseEntity.ok(new ApiResponse<>(new TokenResponse(token.getToken()), HttpStatus.OK.value()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(new ErrorResponse("Unable to generate CSRF token"),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}