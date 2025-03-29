package com.example.smartbasket_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Разрешаем CORS для всех путей на API
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")  // Разрешаем только запросы с фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешаем только эти методы
                .allowedHeaders("*"); // Разрешаем все заголовки
    }
}
