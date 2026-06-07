package com.mark.community.entity;


public class User {
    private String userId;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private boolean deleted;

    public User(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String email, String password, String nickname, String profileImage){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }


    public User(){

    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getNickname(){
        return nickname;
    }

    public String getUserId(){
        return userId;
    }

    public String getProfileImage(){
        return profileImage;
    }

    public boolean isDeleted(){
        return deleted;
    }

}
