package com.mark.community.dto;

public class RegisterResponse {
    private Long userId;

    public RegisterResponse(Long userId) {
        this.userId = userId;
    }
    public RegisterResponse(){

    }

    public Long getUserId(){
        return userId;
    }
}
