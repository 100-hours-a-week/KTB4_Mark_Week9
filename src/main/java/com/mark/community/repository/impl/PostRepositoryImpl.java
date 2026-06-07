package com.mark.community.repository.impl;


import com.mark.community.entity.Post;
import com.mark.community.repository.PostRepository;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final ObjectMapper objectMapper;
    private final String FILE = "db/posts.json";
    private final Map<String, Post> posts = new HashMap<>();
    private int nextPostId = 1;

    public PostRepositoryImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;

        File jsonData = new File(FILE);
        if(jsonData.exists() && jsonData.length() > 0){
            Post[] postData = objectMapper.readValue(jsonData, Post[].class);

            for(Post post : postData){
                posts.put(post.getPostId(), post);
                int num = Integer.parseInt(post.getPostId().substring(1));
                if(num > nextPostId) nextPostId = num + 1;
            }
        }
    }
    private void saveJson(){
        objectMapper.writeValue(new File(FILE), posts.values());
    }

    @Override
    public Post save(Post post){
        if(post.getPostId() == null) {
            post.setPostId("P" + nextPostId++);
        }
        posts.put(post.getPostId(), post);
        saveJson();
        return post;
    }

    @Override
    public Optional<Post> findById(String postId) {
        return Optional.ofNullable(posts.get(postId));
    }

    @Override
    public List<Post> findAllOrderByPostTime(int size, String lastPostId) {
        List<Post> postsData = new ArrayList<>();
        Set<String> postKeySet = posts.keySet();

        for(String key : postKeySet){
            Post post = posts.get(key);
            if(post.isTemp() || post.isDeleted()) continue;
            postsData.add(post);
            if(postsData.size() == size) break;
        }

        return postsData;
    }

}
