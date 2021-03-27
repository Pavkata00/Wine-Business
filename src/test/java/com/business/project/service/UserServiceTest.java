package com.business.project.service;

import com.business.project.model.entity.RoleEntity;
import com.business.project.model.entity.UserEntity;
import com.business.project.model.entity.enums.RoleEnum;
import com.business.project.model.service.UserServiceModel;
import com.business.project.repository.UserRepository;
import com.business.project.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleService mockRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    private UserServiceImpl userService;
    private RoleEntity userRole = createUserRole();
    private RoleEntity adminRole = createAdminRole();



    private UserEntity userEntity = createUserEntity();
    private UserServiceModel userServiceModel = createUserServiceModel();


    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(mockRoleService,mockUserRepository,passwordEncoder,modelMapper);

    }

    @Test
    public void testUsernameExists() {
        when(mockUserRepository.findByUsername("pesho")).thenReturn(Optional.of(userEntity));

        boolean b = userService.usernameExists(userServiceModel);

        Assertions.assertTrue(b);
    }

    @Test
    public void testUsernameInvalid() {
        userServiceModel.setUsername("invalid");

        boolean b = userService.usernameExists(userServiceModel);

        Assertions.assertFalse(b);
    }

    @Test
    public void testEmailExists() {
        when(mockUserRepository.findByEmail("pesho@test.com")).thenReturn(Optional.of(userEntity));

        boolean b = userService.emailExists(userServiceModel);

        Assertions.assertTrue(b);
    }

    @Test
    public void testEmailInvalid() {
        userServiceModel.setEmail("invalid");

        boolean b = userService.emailExists(userServiceModel);

        Assertions.assertFalse(b);
    }

    @Test
    public void testExecuteCommandPromote() {

        when(mockUserRepository.findByUsername("pesho")).thenReturn(Optional.of(userEntity));
        when(mockRoleService.getUserRole()).thenReturn(userRole);
        when(mockRoleService.getAdminRole()).thenReturn(adminRole);

        userServiceModel.setCommand("Promote");
        userService.executeCommand(userServiceModel);

        Assertions.assertEquals(2,userEntity.getRoles().size());
    }

    @Test
    public void testExecuteCommandDemote() {

        when(mockUserRepository.findByUsername("pesho")).thenReturn(Optional.of(userEntity));
        when(mockRoleService.getUserRole()).thenReturn(userRole);
        when(mockRoleService.getAdminRole()).thenReturn(adminRole);

        userServiceModel.setCommand("Demote");
        userService.executeCommand(userServiceModel);

        Assertions.assertEquals(1,userEntity.getRoles().size());
    }

    @Test
    public void testIsAdmin() {
        when(mockUserRepository.findByUsername("pesho")).thenReturn(Optional.of(userEntity));

        boolean b = userService.isAdmin(userServiceModel);

        Assertions.assertFalse(b);
    }

    @Test
    public void testIsAdminInvalid() {
        when(mockUserRepository.findByUsername("invalid")).thenThrow(UsernameNotFoundException.class);

        userServiceModel.setUsername("invalid");

        Assertions.assertThrows(UsernameNotFoundException.class, ()-> {
            userService.isAdmin(userServiceModel);
        });
    }




    private RoleEntity createUserRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleEnum.USER);
        return roleEntity;
    }

    private RoleEntity createAdminRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleEnum.ADMIN);
        return roleEntity;
    }


    private UserEntity createUserEntity() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleEnum.USER);

        UserEntity userEntity = new UserEntity();
        userEntity.setRoles(List.of(roleEntity));
        userEntity.setUsername("pesho");
        userEntity.setPassword("12345");
        userEntity.setFullName("Pesho Petrov");
        userEntity.setEmail("pesho@test.com");

        return userEntity;
    }

    private UserServiceModel createUserServiceModel() {


        UserServiceModel userEntity = new UserServiceModel();
        userEntity.setUsername("pesho");
        userEntity.setPassword("12345");
        userEntity.setFullName("Pesho Petrov");
        userEntity.setEmail("pesho@test.com");



        return userEntity;
    }
}

