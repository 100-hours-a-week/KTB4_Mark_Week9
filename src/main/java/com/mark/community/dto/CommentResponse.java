package com.mark.community.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {
    private String commentId;
    private String nickname;
    private String comment;
    private String userId;
    private boolean deleted;

    public CommentResponse(String commentId){
        this.commentId = commentId;
    }

    public CommentResponse(String nickname, String comment, String userId, boolean deleted){
        this.nickname = nickname;
        this.comment = comment;
        this.userId = userId;
        this.deleted = deleted;
    }

    public String getNickname() {
        return nickname;
    }

    public String getComment() {
        return comment;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getCommentId() {
        return commentId;
    }
}
