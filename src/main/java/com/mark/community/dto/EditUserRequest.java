package com.mark.community.dto;

public class EditUserRequest {
    private String nickname;
    private String password;

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public EditUserRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
