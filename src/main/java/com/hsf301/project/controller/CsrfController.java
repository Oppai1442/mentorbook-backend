package com.hsf301.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsf301.project.model.request.TokenResponse;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.ErrorResponse;

import org.springframework.security.web.csrf.CsrfToken;

@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping("/generate-token")
    public ResponseEntity<Object> csrf(CsrfToken token) {
        if (token != null) {
            return ResponseEntity.ok(new ApiResponse<TokenResponse>(new TokenResponse(token.getToken())));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<ErrorResponse>(new ErrorResponse("Unable to generate CSRF token")));
        }
    }

}