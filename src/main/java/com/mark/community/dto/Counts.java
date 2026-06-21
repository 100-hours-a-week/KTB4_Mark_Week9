package com.mark.community.dto;


import lombok.Getter;

@Getter
public class Counts {
    private long likes;
    private long comments;
    private long views;

    public Counts(long likes, long comments, long views) {
        this.likes = likes;
        this.comments = comments;
        this.views = views;
    }
}
