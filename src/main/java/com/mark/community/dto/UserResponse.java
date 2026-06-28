package com.mark.community.dto;

import lombok.Getter;

@Getter
public class UserResponse {
    private String email;
    private String nickname;
    private Long profileFileId;

    public UserResponse(String email, String nickname, Long profileFileId){
        this.email = email;
        this.nickname = nickname;
        this.profileFileId = profileFileId;
    }
}