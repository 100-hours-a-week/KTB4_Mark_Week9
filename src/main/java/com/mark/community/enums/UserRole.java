package com.mark.community.enums;

public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_AUTH_USER("ROLE_AUTH_USER");

    private String value;

    UserRole(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
