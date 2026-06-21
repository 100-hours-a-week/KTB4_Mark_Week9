package com.mark.community.repository;

import com.mark.community.entity.Like;
import com.mark.community.entity.key.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, PostLikeId> {
    boolean existsById(PostLikeId postLikeId);
    void deleteById(PostLikeId postLikeId);
    long countByIdPostId(Long postId);

}
