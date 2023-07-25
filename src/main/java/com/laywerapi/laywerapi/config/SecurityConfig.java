package com.laywerapi.laywerapi.config;

import com.laywerapi.laywerapi.services.implementation.UserRegistrationDetailsImpl;
import com.laywerapi.laywerapi.shared.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRegistrationDetailsImpl userRegistrationDetails;

    public SecurityConfig(UserRegistrationDetailsImpl userRegistrationDetails) {
        this.userRegistrationDetails = userRegistrationDetails;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("api/v1/registration/**").permitAll()
                        .antMatchers(Constants.BASE_URL + "/clients/**").access("hasRole('USER')")
                        .antMatchers(Constants.BASE_URL + "/users/**").access("hasRole('USER')")
                        .antMatchers(Constants.BASE_URL + "/trials/**").access("hasRole('USER')")
                        .antMatchers(Constants.BASE_URL + "/users/all").access("hasRole('ADMIN')"))
                .userDetailsService(userRegistrationDetails)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(Customizer.withDefaults())
                .formLogin()
                .and()
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
