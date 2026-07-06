package com.mark.community.dto;

import java.util.List;

public class PostRequest {
    private String title;
    private String body;
    private List<String> images;

    public PostRequest(String title, String body, List<String> images) {
        this.title = title;
        this.body = body;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public List<String> getImages() {
        return images;
    }
}
