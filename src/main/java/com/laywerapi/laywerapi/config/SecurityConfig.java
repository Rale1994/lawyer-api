package com.laywerapi.laywerapi.config;

//import com.laywerapi.laywerapi.services.implementation.UserDetailServiceImpl;
import com.laywerapi.laywerapi.services.implementation.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserServiceImpl userServiceImpl;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("api/v1/users/**").permitAll()
                        .antMatchers("/api/v1/registration").permitAll()
                        .mvcMatchers("api/v1/users/update").hasRole("USER")
                        .mvcMatchers("api/v1/clients/**").hasRole("USER")
                        .mvcMatchers("api/v1/users/all").hasRole("ADMIN"))
                .userDetailsService(userServiceImpl)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
