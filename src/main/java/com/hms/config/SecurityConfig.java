package com.hms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    )throws Exception {
        //h(cd)2
        //csrf-> Cross Site Request Forgery Protection
        http.csrf().disable().cors().disable();
        //cors-> Cross Origin Resource Sharing

        //haap
        http.authorizeHttpRequests().anyRequest().permitAll();
//        http.authorizeHttpRequests().requestMatchers
//                ("/api/v1/auth/signup","/api/v1/auth/signIn",
//                        "/api/v1/auth/content-manager-signup","/api/v1/auth/blog-manager-signup",
//                        "/api/v1/auth/login-otp")
//                .permitAll()
//                .requestMatchers("/api/v1/cars/add")//.hasRole("CONTENT-MANAGER")for single user
//                .hasAnyRole("CONTENT-MANAGER","BLOG-MANAGER")//multiple roles
//                .anyRequest().authenticated();

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

        return http.build();
    }
}
