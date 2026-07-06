package com.mark.community.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {

    private String email;
    private String password;
    private String nickname;

    public RegisterRequest(){

    }

    public RegisterRequest(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
