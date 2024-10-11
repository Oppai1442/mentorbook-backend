package com.hsf301.project.config.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.csrf.enabled}")
    private boolean csrfEnabled;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //
        if (csrfEnabled) {
            http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );
        } else {
            http.csrf(
                csrf -> csrf.disable()
            ); 
        }
        
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/token/csrf").permitAll() // Cho phép truy cập vào endpoint /login
                .anyRequest().permitAll() // Cần xác thực cho các yêu cầu khác
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
