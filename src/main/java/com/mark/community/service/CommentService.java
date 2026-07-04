package com.mark.community.service;

import com.mark.community.dto.CommentRequest;
import com.mark.community.dto.CommentResponse;
import com.mark.community.entity.Comment;
import com.mark.community.entity.Post;
import com.mark.community.entity.User;
import com.mark.community.exception.CustomException;
import com.mark.community.messages.ApiResponseErrorMessage;
import com.mark.community.repository.CommentRepository;
import com.mark.community.repository.PostRepository;
import com.mark.community.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(
            CommentRepository commentRepository,
            UserRepository userRepository,
            PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    public Comment commentSave(Long postId, CommentRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.USER_NOT_FOUND));

        Post post = postRepository.getReferenceById(postId);


        Comment comment = new Comment(
                post,
                user,
                request.getComment(),
                new Date(),
                request.getParentCommentId()
        );

        return commentRepository.save(comment);
    }

    public Comment editComment(Long commentId, CommentRequest commentRequest, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.COMMENT_NOT_FOUND));

        if((!comment.getUser().getId().equals(userId))){
            throw new CustomException(ApiResponseErrorMessage.FORBIDDEN);
        }

        comment.setComment(commentRequest.getComment());
        comment.setCommentTime(new Date());

        return comment;
    }

    public Comment deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ApiResponseErrorMessage.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)){
            throw new CustomException(ApiResponseErrorMessage.FORBIDDEN);
        }

        comment.setDeleted(true);

        return comment;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getUser().getNickname(),
                        comment.isDeleted() ? "삭제된 댓글입니다" : comment.getComment(),
                        comment.getUser().getId(),
                        comment.getParentCommentId(),
                        comment.isDeleted()
                )).toList();

    }
}
