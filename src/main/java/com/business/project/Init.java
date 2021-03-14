package com.business.project;

import com.business.project.service.FactoryService;
import com.business.project.service.RoleService;
import com.business.project.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final FactoryService factoryService;

    public Init(UserService userService, RoleService roleService, FactoryService factoryService) {
        this.userService = userService;
        this.roleService = roleService;
        this.factoryService = factoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.roleService.initRoles();

        this.userService.initAdmin();

        this.factoryService.initFactories();

    }
}
