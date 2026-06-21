package com.mark.community.repository;

import com.mark.community.entity.PostImage;
import com.mark.community.entity.key.PostImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, PostImageId> {
    List<PostImage> findByIdPostId(Long postId);

}
