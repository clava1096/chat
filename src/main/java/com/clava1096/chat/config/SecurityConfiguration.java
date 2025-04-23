package com.clava1096.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/", "/login/*", "/error**").permitAll()
                                .anyRequest().authenticated()
                        )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/user", true))
                .logout(l -> l.logoutSuccessUrl("/logout"));
                return http.build();
    }
}