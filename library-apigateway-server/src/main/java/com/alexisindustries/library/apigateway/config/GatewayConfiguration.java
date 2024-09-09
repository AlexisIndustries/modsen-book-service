package com.alexisindustries.library.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("library-main-service", r -> r.path("/api/v1/books/**", "/api/v1/authors/**", "/api/v1/bookgenres/**")
                        .uri("http://library-main-service:8991/"))
                .route("library-auth-service", r -> r.path("/api/v1/auth/**", "/api/v1/users/**")
                        .uri("http://library-auth-service:8532/"))
                .route("library-reservation-service", r -> r.path("/api/v1/reservations/**")
                        .uri("http://library-reservation-service:8501/"))
                .build();
    }
}
