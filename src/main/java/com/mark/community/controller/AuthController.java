package com.mark.community.controller;

import com.mark.community.messages.ApiResponseMessage;
import com.mark.community.response.ApiResponse;
import com.mark.community.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/csrf")
    public ResponseEntity<?> csrfToken(CsrfToken csrfToken){
        csrfToken.getToken();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_CSRF_TOKEN));
    }
}
