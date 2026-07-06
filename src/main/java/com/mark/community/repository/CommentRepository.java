package com.mark.community.repository;

import com.mark.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByPostId(Long postId);
    List<Comment> findByPostId(Long postId);
    long countByUserId(Long userId);

}
