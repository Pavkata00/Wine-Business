package com.business.project.model.view;

import com.business.project.model.entity.UserEntity;

import java.time.LocalDateTime;

public class ReviewViewModel {

    private String text;
    private int rating;
    private String dateTime;
    private String user;

    public ReviewViewModel() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
