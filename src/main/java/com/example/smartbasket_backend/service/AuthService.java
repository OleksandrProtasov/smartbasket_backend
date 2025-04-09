// com.example.smartbasket_backend.service.AuthService.java
package com.example.smartbasket_backend.service;

import com.example.smartbasket_backend.dto.AuthRequest;
import com.example.smartbasket_backend.dto.AuthResponse;
import com.example.smartbasket_backend.model.User;
import com.example.smartbasket_backend.model.Role;
import com.example.smartbasket_backend.repository.UserRepository;
import com.example.smartbasket_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(String name, String email, String password) {
        User user = new User(name, email, passwordEncoder.encode(password), Role.USER);
        return userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
