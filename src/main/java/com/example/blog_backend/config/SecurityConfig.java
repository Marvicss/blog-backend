package com.example.blog_backend.config;

import com.example.blog_backend.modules.login.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginService loginService;

    public SecurityConfig(LoginService loginService){
        this.loginService = loginService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/**", "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/**", "/posts/**").hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "/user/**", "/posts/**").hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/user/**", "/posts/**").hasAnyRole("ADMIN", "AUTHOR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(loginService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

