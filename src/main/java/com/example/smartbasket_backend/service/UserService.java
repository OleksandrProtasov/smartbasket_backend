package com.example.smartbasket_backend.service;

import com.example.smartbasket_backend.model.Role;
import com.example.smartbasket_backend.model.User;
import com.example.smartbasket_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Регистрация нового пользователя
    public User registerUser(User user) {
        if (user.getRole() == null) {
            user.setRole(Role.valueOf("USER")); // Устанавливаем роль по умолчанию
        }
        return userRepository.save(user);
    }

    // Поиск пользователя по email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
