package com.business.project.model.view;

import java.time.LocalDateTime;

public class LogViewModel {

    private String wine;
    private String username;
    private LocalDateTime localDateTime;
    private String action;

    public LogViewModel() {
    }

    public String getWine() {
        return wine;
    }

    public void setWine(String wine) {
        this.wine = wine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
