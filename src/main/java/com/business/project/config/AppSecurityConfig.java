package com.business.project.config;

import com.business.project.service.impl.WineAppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final WineAppUserDetailsService wineAppUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfig(WineAppUserDetailsService wineAppUserDetailsService, PasswordEncoder passwordEncoder) {
        this.wineAppUserDetailsService = wineAppUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(wineAppUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/js/**", "/css/**", "/img/**").permitAll().
                antMatchers("/","/users/register","/users/login").permitAll().
                antMatchers("/factory/view").permitAll().
                antMatchers("/about").permitAll().
                antMatchers("/wine-rest/api/**").permitAll().
                antMatchers("/contact").permitAll().
                antMatchers("/admin/**").hasRole("ADMIN").
                antMatchers("/wine/**").hasRole("ADMIN").
                antMatchers("/**").authenticated().
                    and().
                formLogin().
                loginPage("/users/login").
                usernameParameter("username").
                passwordParameter("password").
                defaultSuccessUrl("/").
                failureForwardUrl("/users/login-error").
                    and().
                logout().
                logoutUrl("/logout").
                logoutSuccessUrl("/").
                invalidateHttpSession(true).
                deleteCookies("JSESSIONID");
    }
}
