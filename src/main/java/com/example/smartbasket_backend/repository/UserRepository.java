package com.example.smartbasket_backend.repository;

import com.example.smartbasket_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Метод для поиска пользователя по email
    User findByEmail(String email);
}
