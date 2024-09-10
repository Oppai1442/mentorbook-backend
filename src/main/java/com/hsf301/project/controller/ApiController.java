package com.hsf301.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping
    public String helloWorld() {
        return "Hello world";
    }

    @GetMapping("/list")
    public Map<String, String> listAllApis() {
        Map<String, String> apis = new HashMap<>();
        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            apis.put(key.toString(), value.toString());
        });
        return apis;
    }
}
