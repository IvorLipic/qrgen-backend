package com.il.qrgen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/tickets/generate").hasAuthority("SCOPE_generate:ticket") // M2M only
                .requestMatchers("/api/tickets/count").permitAll() // Public endpoint
                .requestMatchers("/api/tickets/**").hasAuthority("SCOPE_openid") // Requires OIDC authentication
                .anyRequest().permitAll()
        );
        // Enable OAuth2 resource server JWT authentication
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter()))
        );
        return http.build();
    }
}
