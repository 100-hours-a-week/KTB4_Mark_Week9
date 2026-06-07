package com.mark.community.repository.impl;

import com.mark.community.entity.Comment;
import com.mark.community.repository.CommentRepository;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private String FILE = "db/comments.json";
    private final ObjectMapper objectMapper;
    private final Map<String, Comment> comments = new HashMap<>();
    private int nextCommentId = 1;

    public CommentRepositoryImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;

        File jsonData = new File(FILE);

        if(jsonData.exists() && jsonData.length() > 0){
            Comment[] commentData = objectMapper.readValue(jsonData, Comment[].class);


            for(Comment comment : commentData){
                comments.put(comment.getCommentId(), comment);
                int num = Integer.parseInt(comment.getCommentId().substring(1));
                if(num >= nextCommentId) nextCommentId = num + 1;
            }
        }
    }

    private void saveJson() {
        objectMapper.writeValue(new File(FILE), comments.values());
    }


    @Override
    public Comment save(Comment comment) {
        if(comment.getCommentId() == null){
            comment.setCommentId("C" + nextCommentId++);
        }

        comments.put(comment.getCommentId(), comment);
        saveJson();
        return comment;
    }

    @Override
    public Optional<Comment> findById(String commentId) {
        return Optional.ofNullable(comments.get(commentId));
    }

    @Override
    public Optional<List<Comment>> findByPostIdLike(String postId) {
        List<Comment> tempList = new ArrayList<>();

        for(Comment comment : comments.values() ){
            if(comment.getPostId().equals(postId)){
                tempList.add(comment);
            }
        }

        return Optional.of(tempList);

    }
}
