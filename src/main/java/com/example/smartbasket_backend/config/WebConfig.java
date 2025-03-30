package com.example.smartbasket_backend.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173") // допустим, фронт на этом порту
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

}
