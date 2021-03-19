package com.business.project.model.binding;

import com.business.project.model.entity.UserEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReviewAddBindingModel {

    private String text;
    private int rating;
    private String dateTime;
    private String user;

    public ReviewAddBindingModel() {
    }

    @NotBlank(message = "You should write review.")
    @Length(min = 20,message = "Review should be at least 20 characters.")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotNull(message = "You must add rating.")
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
