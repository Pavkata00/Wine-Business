package com.business.project.service;

import com.business.project.model.service.UserServiceModel;

public interface UserService {
    void register(UserServiceModel userServiceModel);

    boolean usernameExists(UserServiceModel userServiceModel);

    boolean emailExists(UserServiceModel userServiceModel);

    void initAdmin();
}