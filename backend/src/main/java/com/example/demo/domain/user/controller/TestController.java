package com.example.demo.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.user.dto.request.RegisterRequest;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.TestService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    private final TestService testService;
    @GetMapping("/api")
    public String test(){
        return "hello world";
    }
    @PostMapping
    public ResponseEntity<User> postNewUser(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(testService.register(registerRequest));
    }
    @PatchMapping
    public ResponseEntity<Integer> updateUser(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(testService.updateUser(registerRequest, 1));
    }

}
