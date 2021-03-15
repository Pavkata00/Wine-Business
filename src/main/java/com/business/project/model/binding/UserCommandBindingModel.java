package com.business.project.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class UserCommandBindingModel {

    private String username;
    private String command;

    public UserCommandBindingModel() {
    }

    @NotBlank(message = "Must not be blank!")
    @Length(min = 3, max = 15,message = "Username length must be between 3 and 15 characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "You must select Command!")
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
