package com.uday.chatterbox.controller;

import org.springframework.web.bind.annotation.RestController;

import com.uday.chatterbox.dta.LoginRequest;
import com.uday.chatterbox.model.User;
import com.uday.chatterbox.repositoory.UserRepo;
import com.uday.chatterbox.util.JwtUtil;

import jakarta.validation.Valid;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

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

        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest login) {
        User user = userRepo.findByEmail(login.getEmail());

        if (user == null || !passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(Map.of("message", "Login successful", "user", user, "token", token));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getMethodName() {
        Map<Integer, User> users = userRepo.findAll().stream().collect(Collectors.toMap(User::getId, user -> user));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        // Extract email from token
        String email = jwtUtil.extractUsername(token);

        // Find user by email
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user);
    }
    

}