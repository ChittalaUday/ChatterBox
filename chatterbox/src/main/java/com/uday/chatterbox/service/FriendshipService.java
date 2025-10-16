package com.uday.chatterbox.service;

import com.uday.chatterbox.model.Friendship;
import com.uday.chatterbox.repository.FriendshipRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepo repo;

    public FriendshipService(FriendshipRepo repo) {
        this.repo = repo;
    }

    // Send friend request
    public Friendship sendRequest(Integer senderId, Integer receiverId) {
        Friendship friendship = new Friendship();
        friendship.setUserId(senderId);
        friendship.setFriendId(receiverId);
        friendship.setStatus(Friendship.Status.PENDING);
        friendship.setCreatedAt(LocalDateTime.now());
        return repo.save(friendship);
    }

    // Accept friend request
    public Friendship acceptRequest(Friendship friendship) {
        friendship.setStatus(Friendship.Status.ACCEPTED);
        friendship.setUpdatedAt(LocalDateTime.now());
        return repo.save(friendship);
    }

    // Reject friend request
    public Friendship rejectRequest(Friendship friendship) {
        friendship.setStatus(Friendship.Status.REJECTED);
        friendship.setUpdatedAt(LocalDateTime.now());
        return repo.save(friendship);
    }

    // List friends
    public List<Friendship> getFriends(Integer userId) {
        return repo.findByUserIdOrFriendIdAndStatus(userId, userId, Friendship.Status.ACCEPTED);
    }

    // List pending requests
    public List<Friendship> getPendingRequests(Integer userId) {
        return repo.findByFriendIdAndStatus(userId, Friendship.Status.PENDING);
    }
}
