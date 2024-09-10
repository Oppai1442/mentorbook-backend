package com.hsf301.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Cho phép truy cập vào tất cả các yêu cầu
            );
        return http.build();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(authorize -> authorize
    //             .requestMatchers("/", "/api/list", "/not-found-api").permitAll() // Allow access to / and /api/list
    //             .anyRequest().authenticated() // Require authentication for all other requests
    //         )
    //         .formLogin(withDefaults())
    //         .httpBasic(withDefaults()); // Custom entry point for redirection;
    //     return http.build();
    // }
}
