package com.capitravel.Capitravel.config;

import com.capitravel.Capitravel.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import com.capitravel.Capitravel.service.impl.CustomUserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/register", "/auth/login", "/loginError", "/logout").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/categories/**", "/experiences/**", "/properties/**", "/experiences/reputation/*", "/reservations/experience/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**", "/reservations/**", "/experiences/favorites/**", "experiences/reputation/*/*").hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.POST, "/reservations/**", "/favorites/**", "/users/favorites/**", "experiences/reputation/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.POST, "/users/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.POST, "/categories/**", "/experiences/**", "/properties/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/categories/**", "/experiences/**", "/properties/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/categories/**", "/experiences/**", "/properties/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/reservations/**").hasAnyRole(USER, ADMIN)
                        .requestMatchers(HttpMethod.POST, "/emails/**").permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().hasRole(USER)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized access - You don't have permission to access this resource.\"}");
            response.getWriter().flush();
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}