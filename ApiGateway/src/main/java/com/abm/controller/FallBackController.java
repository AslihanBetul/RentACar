package com.abm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {
    @GetMapping("/auth")
    public ResponseEntity<String> getFallbackAuth(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AuthService is not responding");
    }
    @GetMapping("/user")
    public ResponseEntity<String> getFallbackUser(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UserService is not responding");
    }
    @GetMapping("/rent")
    public ResponseEntity<String> getFallbackRent(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("RentService is not responding");
    }
    @GetMapping("/car")
    public ResponseEntity<String> getFallbackCar(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CarService is not responding");
    }
}

