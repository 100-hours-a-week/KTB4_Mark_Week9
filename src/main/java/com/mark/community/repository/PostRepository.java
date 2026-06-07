package com.mark.community.repository;

import com.mark.community.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById (String postId);
    Post save(Post post);
    Optional<List<Post>> findAllOrderByPostTime(int size, String lastPostId);
}
