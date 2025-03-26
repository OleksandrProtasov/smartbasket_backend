package com.example.smartbasket_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.smartbasket_backend.model")
@EnableJpaRepositories("com.example.smartbasket_backend.repository")
public class SmartbasketBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartbasketBackendApplication.class, args);
	}
}