package com.mark.community.service;

import com.mark.community.dto.CommentRequest;
import com.mark.community.entity.Comment;
import com.mark.community.entity.User;
import com.mark.community.exception.CustomException;
import com.mark.community.messages.ApiResponseErrorMessage;
import com.mark.community.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }


    public Comment commentSave(String postId, CommentRequest request, User user) {
        Comment comment = new Comment(
                postId,
                user.getUserId(),
                request.getComment(),
                new Date(),
                user.getNickname(),
                request.getParentCommentId());
        commentRepository.save(comment);

        return comment;

    }

    public Comment editComment(String commentId, CommentRequest commentRequest, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.COMMENT_NOT_FOUND));

        if(!comment.getUserId().equals(user.getUserId())){
            throw new CustomException(ApiResponseErrorMessage.FORBIDDEN);
        }

        comment.setComment(commentRequest.getComment());
        comment.setCommentTime(new Date());

        commentRepository.save(comment);

        return comment;
    }

    public Comment deleteComment(String commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.COMMENT_NOT_FOUND));

        if(!comment.getUserId().equals(user.getUserId())){
            throw new CustomException(ApiResponseErrorMessage.FORBIDDEN);
        }

        comment.setDeleted(true);

        commentRepository.save(comment);

        return comment;
    }

    public List<Comment> getComments(String postId) {
        List<Comment> comments = commentRepository.findByPostIdLike(postId)
                .orElseThrow(() ->new CustomException(ApiResponseErrorMessage.COMMENT_NOT_FOUND));

        for(Comment comment : comments){
            if(comment.isDeleted()){
                comment.setComment("삭제된 댓글입니다.");
            }
        }

        return comments;
    }
}
