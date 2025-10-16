package com.uday.chatterbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uday.chatterbox.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepo extends JpaRepository<Friendship, Integer> {
    Optional<Friendship> findByUserIdAndFriendId(Integer userId, Integer friendId);

    List<Friendship> findByUserIdOrFriendIdAndStatus(Integer userId1, Integer userId2, Friendship.Status status);

    List<Friendship> findByFriendIdAndStatus(Integer friendId, Friendship.Status status);
}