package com.uday.chatterbox.controller;

import com.uday.chatterbox.model.Friendship;
import com.uday.chatterbox.repository.FriendshipRepo;
import com.uday.chatterbox.service.FriendshipService;
import com.uday.chatterbox.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendshipService service;
    private final FriendshipRepo repo;

    @Autowired
    private JwtUtil jwtUtil;

    public FriendshipController(FriendshipService service, FriendshipRepo repo) {
        this.service = service;
        this.repo = repo;
    }

    // Send friend request
    @PostMapping("/request/{receiverId}")
    public Friendship sendRequest(@RequestHeader("Authorization") String authHeader, @PathVariable Integer receiverId) {
        // Extract user ID from token
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Integer senderId = jwtUtil.extractUserId(token);

        return service.sendRequest(senderId, receiverId);
    }

    // Accept request friendship id
    @PatchMapping("/accept/{id}")
    public Friendship acceptRequest(@PathVariable Integer id) {
        Friendship friendship = repo.findById(id).orElseThrow();
        return service.acceptRequest(friendship);
    }

    // Reject request
    @PatchMapping("/reject/{id}")
    public Friendship rejectRequest(@PathVariable Integer id) {
        Friendship friendship = repo.findById(id).orElseThrow();
        return service.rejectRequest(friendship);
    }

    // List friends
    @GetMapping("/list")
    public List<Friendship> getFriends(@RequestHeader("Authorization") String authHeader) {
        // Extract user ID from token
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Integer userId = jwtUtil.extractUserId(token);

        return service.getFriends(userId);
    }

    // List pending requests
    @GetMapping("/pending")
    public List<Friendship> getPendingRequests(@RequestHeader("Authorization") String authHeader) {
        // Extract user ID from token
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Integer userId = jwtUtil.extractUserId(token);

        return service.getPendingRequests(userId);
    }
}