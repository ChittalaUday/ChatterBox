package com.uday.chatterbox.repositoory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uday.chatterbox.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}
