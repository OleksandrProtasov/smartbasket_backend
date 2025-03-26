package com.example.smartbasket_backend.service;

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
        return userRepository.save(user);
    }

    // Поиск пользователя по email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
