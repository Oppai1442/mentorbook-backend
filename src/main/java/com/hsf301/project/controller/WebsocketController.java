package com.hsf301.project.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @MessageMapping("/sendMessage") 
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        return message;
    }
}
