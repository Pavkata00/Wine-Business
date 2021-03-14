package com.business.project.service;

import com.business.project.model.entity.RoleEntity;

public interface RoleService {
    void initRoles();

    RoleEntity getAdminRole();

    RoleEntity getUserRole();
}
