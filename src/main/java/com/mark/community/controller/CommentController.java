package com.mark.community.controller;

import com.mark.community.dto.CommentRequest;
import com.mark.community.dto.CommentResponse;
import com.mark.community.entity.Comment;
import com.mark.community.entity.User;
import com.mark.community.exception.CustomException;
import com.mark.community.messages.ApiResponseErrorMessage;
import com.mark.community.messages.ApiResponseMessage;
import com.mark.community.response.ApiResponse;
import com.mark.community.service.CommentService;
import com.mark.community.utils.IdempotencyUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> commentSave(
            @RequestBody CommentRequest request,
            @PathVariable("postId") String postId,
            HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }
        User user = (User) session.getAttribute("user");
        String idempotencyKey = httpRequest.getHeader("Idempotency-Key");
        ResponseEntity<?> idemResponseEntity = IdempotencyUtil.getResponse(idempotencyKey);

        if(idemResponseEntity != null) return idemResponseEntity;

        Comment comment = commentService.commentSave(postId, request, user);

        CommentResponse commentResponse = new CommentResponse(comment.getCommentId());

        ResponseEntity<?> responseEntity = ResponseEntity
                .status(ApiResponseMessage.SUCCESS_COMMENT_SAVE.getStatusCode())
                .header("LOCATION", "/comment/" + comment.getCommentId())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_COMMENT_SAVE, commentResponse));

        IdempotencyUtil.setResponse(idempotencyKey, responseEntity);

        return responseEntity;
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<?> editComment(
            @PathVariable String commentId,
            @RequestBody CommentRequest commentRequest,
            HttpServletRequest httpRequest
    ){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }
        User user = (User) session.getAttribute("user");

        Comment comment = commentService.editComment(commentId, commentRequest, user);

        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_UPDATE_COMMENT.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_UPDATE_COMMENT));

    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") String commentId, HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }
        User user = (User) session.getAttribute("user");

        Comment comment = commentService.deleteComment(commentId, user);

        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_DELETE_COMMENT.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_DELETE_COMMENT));
    }


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable("postId") String postId, HttpServletRequest httpRequest){

        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }

        List<Comment> comments = commentService.getComments(postId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment : comments){
             CommentResponse commentResponse =  new CommentResponse(
                     comment.getNickname(),
                     comment.getComment(),
                     comment.getUserId(),
                     comment.isDeleted()
                     );
             commentResponses.add(commentResponse);
        }

        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_GET_COMMENTS.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_GET_COMMENTS, commentResponses));

    }

}
