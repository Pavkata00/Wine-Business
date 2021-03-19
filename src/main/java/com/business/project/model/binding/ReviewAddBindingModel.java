package com.business.project.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReviewAddBindingModel {

    private String description;
    private String rating;
    private String wineName;
    private String dateTime;
    private String user;

    public ReviewAddBindingModel() {
    }

    @NotBlank(message = "You should write review.")
    @Length(min = 20,message = "Review should be at least 20 characters.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank(message = "You must add rating.")
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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

    @NotBlank(message = "You must select which wine you are making review of.")
    public String getWineName() {
        return wineName;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }
}
