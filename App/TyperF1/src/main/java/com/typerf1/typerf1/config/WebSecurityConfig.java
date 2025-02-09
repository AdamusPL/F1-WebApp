package com.typerf1.typerf1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sign-in", "/register").anonymous() // Only unauthenticated users can access

                        .requestMatchers("/about", "/",
                                "/rules", "/images/*", "/css/**", "/js/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/register-user", "/check-data")
                        .permitAll()

                        .requestMatchers("/participants", "/personal-best", "/get-personal-best", "/predict",
                                "/get-sessions", "/post-predictions", "/check-predictions-existence",
                                "/calculate-points-qualifying", "/calculate-points-race", "/calculate-points-sprint",
                                "/get-participant-standings", "/results", "/standings", "/get-full-name",
                                "/get-season-scores", "/get-grandprix-summary", "/world-records", "/get-records")
                        .authenticated()
                )
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }
}
