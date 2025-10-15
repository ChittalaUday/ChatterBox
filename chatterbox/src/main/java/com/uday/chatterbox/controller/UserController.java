package com.uday.chatterbox.controller;

import org.springframework.web.bind.annotation.RestController;

import com.uday.chatterbox.dta.LoginRequest;
import com.uday.chatterbox.model.User;
import com.uday.chatterbox.repositoory.UserRepo;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists. please try again with new email...");
        }
        if (user.getMobile() != null && userRepo.existsByMobile(user.getMobile())) {
            return ResponseEntity.badRequest()
                    .body("Mobile number already exists. please try again with new mobile number...");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginRequest login) {
        // TODO: process POST request
        User user = userRepo.findByEmail(login.getEmail());

        if (user == null || !passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        return ResponseEntity.ok(Map.of("message", "Login successful", "user", user));
    }

    @GetMapping("/users")
    public String getMethodName() {
        return new String("GET Response: ");
    }

}
