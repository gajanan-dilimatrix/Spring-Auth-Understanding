package com.gajanan.Job.Posting.Application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${admin.password}")
    private String adminPassword;
    @Value("${user.password}")
    private String userPassword;

    private static final String JOBS_ENDPOINT = "/jobs/**";
    private static final String ROLE_ADMIN="ADMIN";
    private static final String ROLE_USER="USER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(request->request
                        .requestMatchers(HttpMethod.GET, JOBS_ENDPOINT).hasAnyRole(ROLE_ADMIN, ROLE_USER)
                        .requestMatchers(HttpMethod.POST, JOBS_ENDPOINT).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, JOBS_ENDPOINT).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, JOBS_ENDPOINT).hasRole(ROLE_ADMIN)
                        .anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin= User.withUsername("admin")
                .password(passwordEncoder.encode(adminPassword))
                .roles(ROLE_ADMIN,ROLE_USER)
                .build();

        UserDetails user=User.withUsername("user")
                .password(passwordEncoder.encode(userPassword))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
