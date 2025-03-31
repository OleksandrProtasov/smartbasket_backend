package com.example.smartbasket_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.smartbasket_backend.repository")
@EntityScan(basePackages = "com.example.smartbasket_backend.model")
@SpringBootApplication
public class SmartbasketBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartbasketBackendApplication.class, args);
	}
}