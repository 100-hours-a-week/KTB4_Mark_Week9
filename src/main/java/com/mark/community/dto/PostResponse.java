package com.mark.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse {
    private String postId;
    private String title;
    private String body;
    private String thumbnailId;
    private String nickname;
    private String userId;
    private Counts counts;
    private String postTime;

    private boolean deleted;
    private boolean blind;
    private boolean edited;
    private boolean permission;

    public PostResponse(String postId){
        this.postId = postId;
    }

    public PostResponse(String postId,
                        String title,
                        String body,
                        String thumbnailId,
                        String nickname,
                        String userId,
                        Counts counts,
                        List<String> fileIds,
                        boolean edited,
                        boolean permission){
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.thumbnailId = thumbnailId;
        this.nickname = nickname;
        this.userId = userId;
        this.counts = counts;
        this.fileIds = fileIds;
        this.edited = edited;
        this.permission = permission;
    }

    public PostResponse(String postId,
                        String title,
                        String body,
                        String thumbnailId,
                        String nickname,
                        String userId,
                        Counts counts,
                        String postTime,
                        boolean deleted,
                        boolean blind){
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.thumbnailId = thumbnailId;
        this.nickname = nickname;
        this.userId = userId;
        this.counts = counts;
        this.postTime = postTime;
        this.deleted = deleted;
        this.blind = blind;
    }

    public PostResponse(){

    }

    private List<String> fileIds;


    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isBlind() {
        return blind;
    }

    public boolean isEdited() {
        return edited;
    }

    public Counts getCounts() {
        return counts;
    }

    public String getPostId() {
        return postId;
    }

    public String getPostTime(){
        return postTime;
    }

    public boolean isPermission() {
        return permission;
    }
}
