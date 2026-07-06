package com.mark.community.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private Long profileFileId;
    private Long userId;
    private String userRole;

    public LoginResponse(Long profileFileId, Long userId, String userRole){
        this.profileFileId = profileFileId;
        this.userId = userId;
        this.userRole = userRole;
    }
}
