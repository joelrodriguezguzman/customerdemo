package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/hello")
    public String getGreeting(){
        return "Welcome to the Demo Application!";
    }
}
