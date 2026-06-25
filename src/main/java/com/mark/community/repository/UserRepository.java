package com.mark.community.repository;

import com.mark.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByIdAndDeletedFalse(Long userId);
}
