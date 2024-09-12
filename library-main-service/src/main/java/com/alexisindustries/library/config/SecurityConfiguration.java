package com.alexisindustries.library.config;

import com.alexisindustries.library.security.JwtUserTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtUserTokenFilter jwtUserTokenFilter;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://localhost:8991");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/api/v1/books").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/books/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/books/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/books/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/books").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/books/isbn/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/authors").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/authors/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/authors/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/authors/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/authors").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/bookgenres/").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/bookgenres/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/bookgenres/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/bookgenres/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/bookgenres").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("v3/api-docs/**").permitAll())
                .addFilterBefore(jwtUserTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
