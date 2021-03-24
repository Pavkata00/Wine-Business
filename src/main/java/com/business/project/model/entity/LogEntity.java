package com.business.project.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "logs")
public class LogEntity extends BaseEntity {

    private String wine;
    private String username;
    private LocalDateTime localDateTime;
    private String action;

    public LogEntity() {
    }

    @Column(name = "wine",nullable = false)
    public String getWine() {
        return wine;
    }

    public void setWine(String wine) {
        this.wine = wine;
    }

    @Column(name = "username",nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "date",nullable = false)
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Column(name = "action",nullable = false)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
