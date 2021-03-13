package com.business.project.service.impl;

import com.business.project.model.entity.UserEntity;
import com.business.project.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WineAppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public WineAppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username).
                orElseThrow(() ->new UsernameNotFoundException("User with name " + username + " not found!"));
        return mapUser(userEntity);
    }

    private UserDetails mapUser(UserEntity userEntity) {
        List<GrantedAuthority> grantedAuthorities =
                userEntity.getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getRole().name())).
                        collect(Collectors.toList());

        return new User(userEntity.getUsername(),userEntity.getPassword(),grantedAuthorities);
    }
}
