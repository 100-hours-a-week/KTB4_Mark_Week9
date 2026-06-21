package com.mark.community.dto;

public class CommentRequest {
    private String comment;
    private Long parentCommentId;

    public String getComment() {
        return comment;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

}


