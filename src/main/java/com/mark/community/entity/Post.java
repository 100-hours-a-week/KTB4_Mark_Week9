package com.mark.community.entity;

import java.util.Date;
import java.util.List;

public class Post {
    private String postId;
    private String title;
    private String body;
    private String thumbnailId;
    private String nickname;
    private String userId;
    private Date postTime;

    private List<String> fileIds;

    private int likes;
    private int comments;
    private int views;
    private int reports;

    private boolean deleted;
    private boolean blind;
    private boolean edited;
    private boolean temp = true;

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setThumbnailId(String thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isTemp() {
        return temp;
    }

    public boolean isBlind() {
        return blind;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setBlind(boolean blind) {
        this.blind = blind;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }



    public boolean isEdited() {
        return edited;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getViews() {
        return views;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostId() {
        return postId;
    }

    public Date getPostTime() {
        return postTime;
    }



    public int getReports() {
        return reports;
    }

    public Post(String title, String body, String thumbnailId, String nickname, String userId) {
        this.title = title;
        this.body = body;
        this.thumbnailId = thumbnailId;
        this.nickname = nickname;
        this.userId = userId;
    }

    public Post(){

    }


}

