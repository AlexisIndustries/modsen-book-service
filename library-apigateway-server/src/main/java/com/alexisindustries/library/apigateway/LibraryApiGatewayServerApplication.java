package com.alexisindustries.library.apigateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class LibraryApiGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiGatewayServerApplication.class, args);
    }

}
