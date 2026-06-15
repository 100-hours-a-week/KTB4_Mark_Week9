package com.mark.community.exception;

import com.mark.community.messages.ApiResponseErrorMessage;
import com.mark.community.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    private ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponse> serverError(Exception e){
        return ErrorResponse.toResponseEntity(ApiResponseErrorMessage.SERVER_ERROR);
    }


}
