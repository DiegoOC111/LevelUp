package com.example.LevelUp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Spring API Duoc: Level Up")
                        .version("1.0.0")
                        .description("Backend Fullstack 2 para implementacion semestral caso LevelUpGamers")
        );
    } // cierre del método

} // cierre de la clase
