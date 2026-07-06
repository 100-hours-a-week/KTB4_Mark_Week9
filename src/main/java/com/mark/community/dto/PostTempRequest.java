package com.mark.community.dto;


public class PostTempRequest {
    private String title;
    private String body;

    public PostTempRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
}
