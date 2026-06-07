package com.mark.community.repository;

import com.mark.community.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById (String userId);
    Optional<User> findByEmail (String email);
    User save(User user);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existByUser(String userId);
}
