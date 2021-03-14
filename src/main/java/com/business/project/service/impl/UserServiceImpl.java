package com.business.project.service.impl;

import com.business.project.model.entity.RoleEntity;
import com.business.project.model.entity.UserEntity;
import com.business.project.model.entity.enums.RoleEnum;
import com.business.project.model.service.UserServiceModel;
import com.business.project.repository.UserRepository;
import com.business.project.service.RoleService;
import com.business.project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(RoleService roleService, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserServiceModel userServiceModel) {
        RoleEntity roleEntity = this.roleService.getUserRole();

        UserEntity userEntity = this.modelMapper.map(userServiceModel,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        userEntity.getRoles().add(roleEntity);

        this.userRepository.save(userEntity);

    }

    @Override
    public boolean usernameExists(UserServiceModel userServiceModel) {
        UserEntity userEntity = this.userRepository.findByUsername(userServiceModel.getUsername()).orElse(null);

        if (userEntity==null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean emailExists(UserServiceModel userServiceModel) {
        UserEntity userEntity = this.userRepository.findByEmail(userServiceModel.getEmail()).orElse(null);
        if (userEntity==null) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void initAdmin() {

        if (this.userRepository.count()==0) {
            UserEntity userEntity = new UserEntity();
            userEntity.setFullName("Pavel Naydenov");
            userEntity.setEmail("pavkata.naydenov@gmail.com");
            userEntity.setUsername("admin");
            userEntity.setPassword(passwordEncoder.encode("12345"));

            RoleEntity roleEntityAdmin = this.roleService.getAdminRole();

            RoleEntity roleEntityUser = this.roleService.getUserRole();

            userEntity.setRoles(List.of(roleEntityAdmin,roleEntityUser));
            this.userRepository.save(userEntity);
        }
    }
}
