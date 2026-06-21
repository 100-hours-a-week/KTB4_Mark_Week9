package com.mark.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;
    private boolean deleted = false;
    private Long parentCommentId;
    private Date commentTime;


    public Comment(Post post, User user, String comment, Date commentTime, Long parentCommentId) {
        this.post = post;
        this.user = user;
        this.comment = comment;
        this.commentTime = commentTime;
        this.parentCommentId = parentCommentId;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
