package com.examly.springapp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class WelcomeController {
    
    @GetMapping
    public String home() {
        return "Welcome to Spring Boot Art Gallery Project";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Successfully Logined ! \n Welcome to Spring Boot Art Gallery Project :) ";
    }
    
}
