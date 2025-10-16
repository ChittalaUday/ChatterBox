package com.uday.chatterbox.controller;

import org.springframework.web.bind.annotation.RestController;

import com.uday.chatterbox.dta.LoginRequest;
import com.uday.chatterbox.model.User;
import com.uday.chatterbox.repository.UserRepo;
import com.uday.chatterbox.util.JwtUtil;

import jakarta.validation.Valid;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;

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

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());

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

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> updates) {

        // 1️⃣ Extract token
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        // 2️⃣ Extract email (username) from token
        String email = jwtUtil.extractUsername(token);

        // 3️⃣ Find the user
        User existingUser = userRepo.findByEmail(email);
        if (existingUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // 4️⃣ Prevent sensitive updates
        updates.remove("id");
        updates.remove("email");
        updates.remove("password");
        updates.remove("role");

        // 5️⃣ Apply updates dynamically
        updates.forEach((key, value) -> {
            try {
                java.lang.reflect.Field field = User.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(existingUser, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Ignore invalid fields
            }
        });

        // 6️⃣ Save updated user
        User updatedUser = userRepo.save(existingUser);

        return ResponseEntity.ok(Map.of(
                "message", "User updated successfully",
                "user", updatedUser));
    }

    @PatchMapping("/users/me")
    public ResponseEntity<?> patchUser(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> updates) {
        // 1️⃣ Extract token
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        // 2️⃣ Extract email (username) from token
        String email = jwtUtil.extractUsername(token);

        // 3️⃣ Find the user
        User existingUser = userRepo.findByEmail(email);
        if (existingUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // 4️⃣ Prevent sensitive updates
        updates.remove("id");
        updates.remove("email");
        updates.remove("password");
        updates.remove("role");

        // 5️⃣ Apply updates dynamically
        updates.forEach((key, value) -> {
            try {
                java.lang.reflect.Field field = User.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(existingUser, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Ignore invalid fields
            }
        });

        // 6️⃣ Save updated user
        User updatedUser = userRepo.save(existingUser);

        updatedUser.setPassword(null);
        updatedUser.setRole(null);

        return ResponseEntity.ok(Map.of(
                "message", "User updated successfully",
                "user", updatedUser));
    }

}