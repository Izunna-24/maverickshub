package com.maverickstube.maverickshub.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //used to put bean in a context
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(){
        return null;
    }
}
