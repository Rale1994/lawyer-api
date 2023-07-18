package com.laywerapi.laywerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.laywerapi.laywerapi.*")
@EntityScan("com.laywerapi.laywerapi.*")
@EnableJpaRepositories(basePackages = "com.laywerapi.laywerapi.repositories")
public class
LawyerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LawyerApiApplication.class, args);
    }


}
