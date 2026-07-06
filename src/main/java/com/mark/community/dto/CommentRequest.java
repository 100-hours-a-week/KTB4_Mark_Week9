package com.mark.community.dto;

import lombok.Getter;

@Getter
public class CommentRequest {
    private String comment;
    private Long parentCommentId;

    public CommentRequest(String comment, Long parentCommentId) {
        this.comment = comment;
        this.parentCommentId = parentCommentId;
    }

    public String getComment() {
        return comment;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

}


