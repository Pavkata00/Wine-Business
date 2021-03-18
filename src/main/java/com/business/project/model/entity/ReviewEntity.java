package com.business.project.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

    private String text;
    private int rating;
    private LocalDateTime dateTime;
    private UserEntity user;

    public ReviewEntity() {
    }

    @Column(name = "text",columnDefinition = "TEXT",nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Column(name = "rating",nullable = false)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Column(name = "date_time",nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
