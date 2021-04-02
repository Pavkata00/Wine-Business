package com.business.project.service.impl;

import com.business.project.model.entity.RoleEntity;
import com.business.project.model.entity.enums.RoleEnum;
import com.business.project.repository.RoleRepository;
import com.business.project.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRoles() {

        if (this.roleRepository.count() == 0) {
            Arrays.stream(RoleEnum.values()).forEach(roleEnum -> {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setRole(roleEnum);

                roleRepository.save(roleEntity);
            });
        }
    }

    @Override
    public RoleEntity getAdminRole() {
        return this.roleRepository.
                findByRole(RoleEnum.ADMIN).
                orElseThrow(() -> new IllegalStateException("Please initialize the ADMIN role!"));
    }

    @Override
    public RoleEntity getUserRole() {
        return this.roleRepository.findByRole(RoleEnum.USER).orElseThrow(() -> new IllegalStateException("Please initialize the USER role"));
    }
}
