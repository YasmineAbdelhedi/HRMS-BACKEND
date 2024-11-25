package com.example.hrms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Completely remove csrf() as it's no longer required for JWT-based stateless authentication
                .csrf(AbstractHttpConfigurer::disable)

                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()  // Allow unauthenticated access for auth endpoints
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Allow Swagger UI and API docs access
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Only accessible by ADMIN role
                        .requestMatchers("/hrmanager/**").hasRole("HR_MANAGER")  // Only accessible by HRMANAGER role
                        .requestMatchers("/prmanager/**").hasRole("PROJECT_MANAGER")  // Only accessible by PR MANAGER role
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")  // Only accessible by EMPLOYEE role
                        .requestMatchers("/public/**").permitAll()  // Publicly accessible
                        .anyRequest().authenticated()            // Other requests require authentication
                )


                // Set stateless session management for JWT (no session creation)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Add JWT filter to the filter chain
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // Use the custom authentication provider for login
                .authenticationProvider(authenticationProvider);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8005"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}
