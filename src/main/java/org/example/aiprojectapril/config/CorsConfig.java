package org.example.aiprojectapril.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        System.out.println(">>> CORS CONFIG IS ACTIVE <<<");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // ← tillad ALLE endpoints
                        .allowedOriginPatterns("*") // ← tillad ALLE origins
                        .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE") // ← alle metoder
                        .allowedHeaders("*"); // ← alle headers
            }
        };
    }
}
